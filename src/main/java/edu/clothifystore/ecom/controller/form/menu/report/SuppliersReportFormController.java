package edu.clothifystore.ecom.controller.form.menu.report;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.ReportGenerator;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SuppliersReportFormController implements Initializable, MenuForm {
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {}

	@Override
	public void update () {}

	@FXML
	public void allSuppliersReportButtonOnAction (ActionEvent actionEvent) {
		UtilFactory.getObject(ReportGenerator.class).startGenerateReport("all_suppliers_report", "supplier/", "all-suppliers-report", null);
	}
}
