package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.DBConnection;
import edu.clothifystore.ecom.util.UserConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReportsFormController implements MenuForm {
	@Override
	public void update () {}

	@FXML
	public void dailyEmployeeReportOnAction (ActionEvent actionEvent) {
		String pdfPath = "";

		try {
			final JasperDesign design = JRXmlLoader.load("src/main/resources/report/employee_daily_report.jrxml");
			final JasperReport report = JasperCompileManager.compileReport(design);
			final JasperPrint print = JasperFillManager.fillReport(report, null, DBConnection.getInstance().getConnection());
			final LocalDateTime time = LocalDateTime.now();
			final String now = String.format("%d%02d%02d_%02d%02d", time.getYear(), time.getMonthValue(), time.getDayOfMonth(), time.getHour(), time.getMinute());
			pdfPath = String.format("%semployee-%s.pdf", UserConfig.getConfiguration("report_save_directory"), now);
			final Path directoryPath = Path.of(UserConfig.getConfiguration("report_save_directory"));

			if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);

			JasperExportManager.exportReportToPdfFile(print, pdfPath);

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
		} catch (JRException | SQLException exception) {
			System.out.println(exception.getMessage());
			new Alert(Alert.AlertType.WARNING, "Failed to generate PDF report.").show();
		} catch (IOException exception) {
			System.out.println(exception.getMessage());

			new Alert(Alert.AlertType.WARNING, "Failed to open the folder. But you can found the report on (" + pdfPath + ") location.").show();
		}
	}
}
