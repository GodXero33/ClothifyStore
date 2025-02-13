package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReportsFormController implements MenuForm {
	@Override
	public void update () {}

	@FXML
	public void dailyEmployeeReportOnAction (ActionEvent actionEvent) {
		try {
			final JasperDesign design = JRXmlLoader.load("src/main/resources/report/employee_daily_report.jrxml");
			final JasperReport report = JasperCompileManager.compileReport(design);
			final JasperPrint print = JasperFillManager.fillReport(report, null, DBConnection.getInstance().getConnection());

			final LocalDateTime time = LocalDateTime.now();
			final String now = String.format("%d%02d%02d_%02d%02d", time.getYear(), time.getMonthValue(), time.getDayOfMonth(), time.getHour(), time.getMinute());
			final String pdfPath = String.format("E:\\Downloads\\employee-%s.pdf", now);
			JasperExportManager.exportReportToPdfFile(print, pdfPath);

//			new Alert(Alert.AlertType.INFORMATION, "Report has saved as (" + pdfPath + ")").show();
			Runtime.getRuntime().exec("rundll32.exe shell32.dll ShellExec_RunDLL " + pdfPath);
//			JasperViewer.viewReport(print, false);
		} catch (JRException | SQLException | RuntimeException | IOException exception) {
			System.out.println(exception.getMessage());
			new Alert(Alert.AlertType.WARNING, "Failed to generate PDF report.").show();
		}
	}
}
