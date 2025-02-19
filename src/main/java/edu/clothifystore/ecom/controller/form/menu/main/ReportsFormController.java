package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.DBConnection;
import edu.clothifystore.ecom.util.UserConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ReportsFormController implements MenuForm {
	@Override
	public void update () {}

	private String generateReport (String source, String folder, String fileName, Map<String, Object> parameters) throws JRException, SQLException, IOException {
		String pdfPath = "";

		final JasperDesign design = JRXmlLoader.load("src/main/resources/report/" + source + ".jrxml");
		final JasperReport report = JasperCompileManager.compileReport(design);
		final JasperPrint print = JasperFillManager.fillReport(report, parameters, DBConnection.getInstance().getConnection());
		final LocalDateTime timeNow = LocalDateTime.now();
		final String now = String.format("%d%02d%02d_%02d%02d", timeNow.getYear(), timeNow.getMonthValue(), timeNow.getDayOfMonth(), timeNow.getHour(), timeNow.getMinute());
		pdfPath = String.format("%s%s%s-%s.pdf", UserConfig.getConfiguration("report_save_directory"), folder, fileName, now);
		final Path directoryPath = Path.of(UserConfig.getConfiguration("report_save_directory") + folder);

		if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);

		JasperExportManager.exportReportToPdfFile(print, pdfPath);

		return pdfPath;
	}

	private void requestOpenFolderLocation (String pdfPath) throws IOException {
		final File pdfFile = new File(pdfPath);

		if (pdfFile.exists()) {
			final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

			alert.setTitle("Report");
			alert.setHeaderText("Report generated");
			alert.setContentText("The report has generated on location (" + pdfPath + ").'\n\nDo you want to open it now?");

			if (alert.showAndWait().get() != ButtonType.OK) return;

			final ProcessBuilder processBuilder = new ProcessBuilder("explorer.exe", "/select,", pdfFile.getAbsolutePath());
			processBuilder.start();
		} else {
			new Alert(Alert.AlertType.ERROR, "PDF file wasn't created").show();
			throw new IOException("PDF file wasn't created");
		}
	}

	private void startGenerateReport(String source, String folder, String fileName, Map<String, Object> parameters) {
		Alert loadingAlert = new Alert(Alert.AlertType.INFORMATION);
		loadingAlert.setTitle("Generating Report...");
		loadingAlert.setHeaderText("Please Wait...");

		ProgressIndicator progressIndicator = new ProgressIndicator();
		StackPane content = new StackPane(progressIndicator);
		content.setPrefSize(250, 150);

		DialogPane dialogPane = loadingAlert.getDialogPane();
		dialogPane.setContent(content);
		dialogPane.setPrefSize(300, 200);

		loadingAlert.show();

		try {
			String pdfPath = this.generateReport(source, folder, fileName, parameters); // Generate report

			loadingAlert.close();
			this.requestOpenFolderLocation(pdfPath);
		} catch (SQLException | JRException exception) {
			loadingAlert.close();
			new Alert(Alert.AlertType.ERROR, "Something went wrong with creating PDF.").show();
		} catch (IOException exception) {
			loadingAlert.close();
			new Alert(Alert.AlertType.ERROR, "Something went wrong with opening PDF.").show();
		}
	}

	@FXML
	public void dailyEmployeeReportOnAction (ActionEvent actionEvent) {
		final Map<String, Object> parameters = new HashMap<>();
		final LocalDateTime timeNow = LocalDateTime.now();

		parameters.put("time", String.format("%02d : %02d", timeNow.getHour(), timeNow.getMinute()));
		parameters.put("date", String.format("%d/%02d/%02d", timeNow.getYear(), timeNow.getMonthValue(), timeNow.getDayOfMonth()));
		parameters.put("generatedBy", FormController.getInstance().getCurentEmployee().getUserName());
		parameters.put("employeeID", 1);

		this.startGenerateReport("employee_daily_report", "employee/", "employee", parameters);
	}
}
