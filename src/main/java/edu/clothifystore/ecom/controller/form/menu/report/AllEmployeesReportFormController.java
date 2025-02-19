package edu.clothifystore.ecom.controller.form.menu.report;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.ReportGenerator;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AllEmployeesReportFormController implements Initializable, MenuForm {
	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {

	}

	@Override
	public void update () {

	}

	@FXML
	public void activeEmployeesButtonOnAction (ActionEvent actionEvent) {
		UtilFactory.getObject(ReportGenerator.class).startGenerateReport("active_employees_report", "employee/active-employees/", "active-employees", null);
	}

	@FXML
	public void deletedEmployeesButtonOnAction (ActionEvent actionEvent) {
		UtilFactory.getObject(ReportGenerator.class).startGenerateReport("deleted_employees_report", "employee/deleted-employees/", "deleted-employees", null);
	}

	@FXML
	public void adminsButtonOnAction (ActionEvent actionEvent) {
		UtilFactory.getObject(ReportGenerator.class).startGenerateReport("admin_employees_report", "employee/admins/", "admins", null);
	}

	@FXML
	public void employeesByRoleButtonOnAction (ActionEvent actionEvent) {
		UtilFactory.getObject(ReportGenerator.class).startGenerateReport("employees_by_role", "employee/employees-by-role/", "employees_by_role", null);
	}
}
