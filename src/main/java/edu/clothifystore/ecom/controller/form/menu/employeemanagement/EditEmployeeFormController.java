package edu.clothifystore.ecom.controller.form.menu.employeemanagement;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.dto.EmployeePhone;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.PhoneType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditEmployeeFormController extends AddEditEmployeeFormController {
	@FXML
	public Button employeeEditFormClearButton;
	@FXML
	public Label titleLabel;

	private boolean isInSearchMode;
	private Integer searchedEmployeeID;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		super.initialize(null, null);
		this.changeStatusToSearch();
	}

	private void updateToggleTypeControllersDisableStatus (boolean status) {
		this.fullNameTextField.setDisable(status);
		this.emailTextField.setDisable(status);
		this.addressTextField.setDisable(status);
		this.dobDateComboBox.setDisable(status);
		this.dobMonthComboBox.setDisable(status);
		this.dobYearComboBox.setDisable(status);
		this.salaryTextField.setDisable(status);
		this.roleTextField.setDisable(status);
		this.supervisorUsernameTextField.setDisable(status);
		this.phone1TextField.setDisable(status);
		this.phone1TypeComboBox.setDisable(status);
		this.phone2TextField.setDisable(status);
		this.phone2TypeComboBox.setDisable(status);
		this.phone3TextField.setDisable(status);
		this.phone3TypeComboBox.setDisable(status);
		this.asAdminCheckBox.setDisable(status);
	}

	@Override
	public void update () {
		super.update();
		this.changeStatusToSearch();
	}

	private void changeStatusToSearch () {
		this.isInSearchMode = true;

		this.updateToggleTypeControllersDisableStatus(true);
		this.employeeEditFormActionButton.setText("Search");
		this.titleLabel.setText("Search Employee");
		this.employeeEditFormClearButton.setDisable(true);
	}

	private void changeStatusToUpdate () {
		this.isInSearchMode = false;

		this.updateToggleTypeControllersDisableStatus(false);
		this.employeeEditFormActionButton.setText("Update");
		this.titleLabel.setText("Update Employee");
		this.employeeEditFormClearButton.setDisable(false);
	}

	private void updateFieldsWithEmployee (Employee employee) {
		final String[] datePieces = employee.getDOB().split("-"); // 2001-12-19 -> [2001, 12, 19]
		final List<EmployeePhone> employeePhones = employee.getPhone();
		final int employeePhonesSize = employeePhones.size();

		this.userNameTextField.setText(employee.getUserName());
		this.fullNameTextField.setText(employee.getFullName());
		this.nicTextField.setText(employee.getNIC());
		this.emailTextField.setText(employee.getEmail() == null ? "" : employee.getEmail());
		this.addressTextField.setText(employee.getAddress());
		this.salaryTextField.setText(employee.getSalary() == null || employee.getSalary() == 0.0 ? "" : employee.getSalary().toString());
		this.roleTextField.setText(employee.getRole());
		this.asAdminCheckBox.setSelected(employee.getType().equalsIgnoreCase("ADMIN"));
		this.dobYearComboBox.setValue(datePieces[0]);
		this.dobMonthComboBox.setValue(datePieces[1]);
		this.dobDateComboBox.setValue(datePieces[2]);

		for (int a = 0; a < employeePhonesSize; a++) {
			final EmployeePhone phone = employeePhones.get(a);

			if (a == 0) {
				this.phone1TextField.setText(phone.getPhone());
				this.phone1TypeComboBox.setValue(PhoneType.fromString(phone.getType()));
			} else if (a == 1) {
				this.phone2TextField.setText(phone.getPhone());
				this.phone2TypeComboBox.setValue(PhoneType.fromString(phone.getType()));
			} else {
				this.phone3TextField.setText(phone.getPhone());
				this.phone3TypeComboBox.setValue(PhoneType.fromString(phone.getType()));
			}
		}

		if (employee.getAdminID() != null) {
			final String adminUsername = this.employeeService.getAdminName(employee.getAdminID());

			if (adminUsername != null) this.supervisorUsernameTextField.setText(adminUsername);
		}

		this.changeStatusToUpdate();
	}

	private void searchEmployee () {
		final String userName = this.userNameTextField.getText().trim().toLowerCase();
		final String nic = this.nicTextField.getText().trim().toLowerCase();
		final InputValidator inputValidator = UtilFactory.getInstance().getObject(InputValidator.class);

		if (!userName.isEmpty() && !inputValidator.isValidUsername(userName)) {
			this.invalidInputValueOnEmployeeAdd("Incorrect user name. Please check the user name before search.", this.userNameTextField);
			return;
		}

		if (!nic.isEmpty() && !inputValidator.isValidNIC(nic)) {
			this.invalidInputValueOnEmployeeAdd("Incorrect NIC. Please check the NIC before search.", this.nicTextField);
			return;
		}

		final Employee searchedEmployee;

		if (!userName.isEmpty()) {
			searchedEmployee = this.employeeService.get(userName);
		} else if (!nic.isEmpty()) {
			searchedEmployee = this.employeeService.getByNIC(nic);
		} else {
			this.invalidInputValueOnEmployeeAdd("Please fill username or NIC fields before search.", this.userNameTextField);
			return;
		}

		if (searchedEmployee == null) {
			new Alert(Alert.AlertType.WARNING, "No employee found by given data.").show();
			return;
		}

		this.searchedEmployeeID = searchedEmployee.getId();

		new Alert(Alert.AlertType.INFORMATION, "Employee has found. Please update employee data.").show();
		this.updateFieldsWithEmployee(searchedEmployee);
	}

	private void updateEmployee () {
		final Employee newEmployee = this.validateInputsAndGetNewEmployee(true);

		if (newEmployee == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Update employee");
		alert.setHeaderText("Update employee confirm.");
		alert.setContentText("Are you sure that you want to update the employee?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		newEmployee.setId(this.searchedEmployeeID); // Add ID to new employee from searched employee entity.

		if (this.employeeService.update(newEmployee)) {
			new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully.").show();
			this.clearInputData();
			this.changeStatusToSearch();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to update new employee.").show();
		}
	}

	@FXML
	public void employeeEditFormActionButtonOnAction (ActionEvent actionEvent) {
		if (this.isInSearchMode) {
			this.searchEmployee();
		} else {
			this.updateEmployee();
		}
	}

	public void employeeEditFormClearButtonOnAction (ActionEvent actionEvent) {
		this.clearInputData();
		this.changeStatusToSearch();
	}
}
