package edu.clothifystore.ecom.controller.form.menu.employeemanagement;

import edu.clothifystore.ecom.dto.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddEmployeeFormController extends EmployeeEditController {
	@FXML
	public void employeeEditFormActionButtonOnAction (ActionEvent actionEvent) {
		final Employee newEmployee = this.validateInputsAndGetNewEmployee(false);

		if (newEmployee == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Add new employee");
		alert.setHeaderText("Add new employee confirm.");
		alert.setContentText("Are you sure that you want to add new employee?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.employeeService.add(newEmployee)) {
			new Alert(Alert.AlertType.INFORMATION, "New employee added successfully.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to add new employee.").show();
		}
	}
}
