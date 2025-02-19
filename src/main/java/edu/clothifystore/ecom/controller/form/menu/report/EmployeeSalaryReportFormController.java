package edu.clothifystore.ecom.controller.form.menu.report;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ReportGenerator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EmployeeSalaryReportFormController implements Initializable, MenuForm {
	@FXML
	public TextField idTextField;
	@FXML
	public Label fullnameLabel;
	@FXML
	public TextField nictextField;
	@FXML
	public TextField usernameTextField;
	@FXML
	public Button generateReportButton;

	private final EmployeeService employeeService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.EMPLOYEE);
	private Employee loadedEmployee;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.resetForm();
	}

	@Override
	public void update () {
		this.resetForm();
	}

	private void resetForm () {
		this.generateReportButton.setDisable(true);
		this.fullnameLabel.setText("");
		this.idTextField.clear();
		this.nictextField.clear();
		this.usernameTextField.clear();

		this.loadedEmployee = null;
	}

	private void employeeReceived (Employee employee) {
		if (employee == null) {
			new Alert(Alert.AlertType.WARNING, "No employee found matched with any given data.").show();
			return;
		}

		this.generateReportButton.setDisable(false);
		this.fullnameLabel.setText(employee.getFullName());
		this.idTextField.setText(employee.getId().toString());
		this.nictextField.setText(employee.getNIC());
		this.usernameTextField.setText(employee.getUserName());

		this.loadedEmployee = employee;
	}

	@FXML
	public void searchButtonOnAction (ActionEvent actionEvent) {
		final String id = this.idTextField.getText().trim();
		final String nic = this.nictextField.getText().trim();
		final String username = this.usernameTextField.getText().trim().toLowerCase();
		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);
		final boolean[] invalidInputs = new boolean[] { false, false, false }; // ID, NIC, Username

		if (id.isEmpty() && nic.isEmpty() && username.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Nothing to search. Please fill any text field and try again.").show();
			this.idTextField.requestFocus();
			return;
		}

		if (!id.isEmpty()) {
			if (inputValidator.isNonZeroPositiveInteger(id)) {
				this.employeeReceived(this.employeeService.get(Integer.parseInt(id)));
				return;
			}

			invalidInputs[0] = true;
		}

		if (!nic.isEmpty()) {
			if (inputValidator.isValidNIC(nic)) {
				this.employeeReceived(this.employeeService.getByNIC(nic));
				return;
			}

			invalidInputs[1] = true;
		}

		if (!username.isEmpty()) {
			if (inputValidator.isValidUsername(username)) {
				this.employeeReceived(this.employeeService.get(username));
				return;
			}

			invalidInputs[2] = true;
		}

		if (invalidInputs[0]) {
			new Alert(Alert.AlertType.WARNING, "Invalid ID format.").show();
			this.idTextField.requestFocus();
			return;
		}

		if (invalidInputs[1]) {
			new Alert(Alert.AlertType.WARNING, "Invalid NIC format.").show();
			this.nictextField.requestFocus();
			return;
		}

		new Alert(Alert.AlertType.WARNING, "Invalid Username format.").show();
		this.usernameTextField.requestFocus();
	}

	@FXML
	public void generateReportButtonOnAction (ActionEvent actionEvent) {
		if (this.loadedEmployee == null) {
			new Alert(Alert.AlertType.WARNING, "No employee data has been loaded.").show();
			return;
		}

		final ReportGenerator reportGenerator = UtilFactory.getObject(ReportGenerator.class);
		final Map<String, Object> parameters = new HashMap<>();

		parameters.put("employeeID", this.loadedEmployee.getId());

		reportGenerator.startGenerateReport("employee_salary_report", "employee/salaries/", "employee-salary", parameters);
		this.resetForm();
	}

	@FXML
	public void clearButtonOnAction (ActionEvent actionEvent) {
		this.resetForm();
	}
}
