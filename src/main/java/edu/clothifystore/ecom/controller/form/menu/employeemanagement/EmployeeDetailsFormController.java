package edu.clothifystore.ecom.controller.form.menu.employeemanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EmployeeDetailsFormController implements MenuForm {
	@FXML
	public TextField userNameTextField;
	@FXML
	public TextField nicTextField;

	private final EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

	@Override
	public void update () {
		this.userNameTextField.clear();
		this.nicTextField.clear();
	}

	private void generateEmployeeDetails (Employee employee) {
		if (employee == null) return; // In case method invoke with empty employee data.

		final Alert alert = new Alert(Alert.AlertType.INFORMATION);

		alert.setTitle("Employee: " + employee.getUserName());
		alert.setHeaderText("Employee found!");
		alert.setContentText(String.format(
			"Username: %s\nFull Name: %s\nNIC: %s\nAddress: %s\nBirth Day: %s\nSalary: %s\nIs supervisor: %s\nJob Role: %s\n",
			employee.getUserName(),
			employee.getFullName(),
			employee.getNIC(),
			employee.getAddress(),
			employee.getDOB(),
			employee.getSalary() + " Rs",
			employee.getType().equalsIgnoreCase("admin") ? "Yes" : "No",
			employee.getRole()
		));
		alert.show();
	}

	@FXML
	public void employeeSearchButtonOnAction (ActionEvent actionEvent) {
		final String username = this.userNameTextField.getText().trim().toLowerCase();
		final String nic = this.nicTextField.getText().trim().toLowerCase();
		final InputValidator inputValidator = UtilFactory.getInstance().getObject(InputValidator.class);

		if (!username.isEmpty() && !inputValidator.isValidUsername(username)) {
			new Alert(Alert.AlertType.WARNING, "Invalid Username.").show();
			this.userNameTextField.requestFocus();
			return;
		}

		if (!nic.isEmpty() && !inputValidator.isValidNIC(nic)) {
			new Alert(Alert.AlertType.WARNING, "Invalid NIC.").show();
			this.nicTextField.requestFocus();
			return;
		}

		final Employee searchedEmployee;

		if (!username.isEmpty()) {
			searchedEmployee = this.employeeService.get(username);
		} else if (!nic.isEmpty()) {
			searchedEmployee = this.employeeService.getByNIC(nic);
		} else {
			new Alert(Alert.AlertType.WARNING, "Please add fill Username or NIC text fields before search.").show();
			this.userNameTextField.requestFocus();
			return;
		}

		if (searchedEmployee == null) {
			new Alert(Alert.AlertType.WARNING, "There is no employee found with given data.").show();
			this.userNameTextField.requestFocus();
			return;
		}

		this.generateEmployeeDetails(searchedEmployee);
	}
}
