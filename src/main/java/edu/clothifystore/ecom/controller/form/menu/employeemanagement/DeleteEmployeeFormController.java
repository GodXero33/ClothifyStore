package edu.clothifystore.ecom.controller.form.menu.employeemanagement;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteEmployeeFormController implements Initializable, MenuForm {
	@FXML
	public TextField userNameTextField;
	@FXML
	public TextField nicTextField;
	@FXML
	public Label fullNameLabel;
	@FXML
	public Button employeeDeleteButton;

	private final EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);
	private Employee loadedEmployee;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.employeeDeleteButton.setDisable(true);
	}

	@Override
	public void update () {}

	@FXML
	public void employeeSearchButtonOnAction (ActionEvent actionEvent) {
		final String username = this.userNameTextField.getText().trim().toLowerCase();
		final String nic = this.nicTextField.getText().trim().toLowerCase();
		final InputValidator inputValidator = UtilFactory.getInstance().getObject(InputValidator.class);

		if (username.equals(FormController.getInstance().getCurentEmployee().getUserName()) || nic.equals(FormController.getInstance().getCurentEmployee().getNIC())) { // A user can't delete himself.
			new Alert(Alert.AlertType.WARNING, "You can't delete yourself. Please contact another supervisor or logged in as admin.").show();
			this.userNameTextField.clear();
			this.nicTextField.clear();
			return;
		}

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

		if (!username.isEmpty()) {
			this.loadedEmployee = this.employeeService.get(username);
		} else if (!nic.isEmpty()) {
			this.loadedEmployee = this.employeeService.getByNIC(nic);
		} else {
			new Alert(Alert.AlertType.WARNING, "Please add fill Username or NIC text fields before search.").show();
			this.userNameTextField.requestFocus();
			return;
		}

		if (this.loadedEmployee == null) {
			new Alert(Alert.AlertType.WARNING, "There is no employee found with given data.").show();
			this.userNameTextField.requestFocus();
			return;
		}

		this.fullNameLabel.setText(this.loadedEmployee.getFullName());
		this.employeeDeleteButton.setDisable(false);
	}

	@FXML
	public void employeeDeleteButtonOnAction (ActionEvent actionEvent) {
		if (this.loadedEmployee == null) return; // In case somehow execute this method without setting or loading employee data.

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Delete employee");
		alert.setHeaderText("Delete employee confirm.");
		alert.setContentText("Are you sure that you want to delete this employee?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.employeeService.delete(this.loadedEmployee.getId())) {
			new Alert(Alert.AlertType.INFORMATION, "Employee has deleted successfully.").show();
			this.userNameTextField.clear();
			this.nicTextField.clear();
			this.fullNameLabel.setText("");
			this.employeeDeleteButton.setDisable(true);
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to delete the employee.").show();
		}
	}
}
