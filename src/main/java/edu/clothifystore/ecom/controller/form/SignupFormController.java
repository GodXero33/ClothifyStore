package edu.clothifystore.ecom.controller.form;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.UserService;
import edu.clothifystore.ecom.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SignupFormController implements Initializable {
	@FXML
	public TextField userNameTextField;
	@FXML
	public PasswordField passwordPasswordField;
	@FXML
	public TextField passwordTextField;
	@FXML
	public PasswordField confirmPasswordPasswordField;
	@FXML
	public TextField confirmPasswordTextField;
	@FXML
	public TextField initialsTextField;
	@FXML
	public TextField firstNameTextField;
	@FXML
	public TextField lastNameTextField;
	@FXML
	public TextField nicTextField;
	@FXML
	public TextField emailTextField;
	@FXML
	public TextField addressTextField;
	@FXML
	public TextField dobTextField;
	@FXML
	public TextField salaryTextField;
	@FXML
	public TextField roleTextField;
	@FXML
	public TextField adminNameTextField;
	@FXML
	public CheckBox asAdminCheckBox;

	private Map<String, TextField> fxmlTextFieldsMap;
	private final UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.fxmlTextFieldsMap = new LinkedHashMap<>();

		this.fxmlTextFieldsMap.put("User Name", this.userNameTextField);
		this.fxmlTextFieldsMap.put("Initials", this.initialsTextField);
		this.fxmlTextFieldsMap.put("First Name", this.firstNameTextField);
		this.fxmlTextFieldsMap.put("NIC", this.nicTextField);
		this.fxmlTextFieldsMap.put("Address", this.addressTextField);
		this.fxmlTextFieldsMap.put("Birth Day", this.dobTextField);
		this.fxmlTextFieldsMap.put("Role", this.roleTextField);
	}

	// Toggle visibility between password 'password field' and password 'text field' and focus visibility enabled field.
	@FXML
	public void showPasswordCheckBoxOnAction (ActionEvent actionEvent) {
		final boolean isPasswordFieldNotVisible = ((CheckBox) actionEvent.getTarget()).isSelected();

		this.passwordPasswordField.setVisible(!isPasswordFieldNotVisible);
		this.passwordTextField.setVisible(isPasswordFieldNotVisible);
		(isPasswordFieldNotVisible ? this.passwordTextField : this.passwordPasswordField).requestFocus();
	}

	// Toggle visibility between confirm password 'password field' and confirm password 'text field' and focus visibility enabled field.
	@FXML
	public void showConfirmPasswordCheckBoxOnAction (ActionEvent actionEvent) {
		final boolean isConfirmPasswordFieldNotVisible = ((CheckBox) actionEvent.getTarget()).isSelected();

		this.confirmPasswordPasswordField.setVisible(!isConfirmPasswordFieldNotVisible);
		this.confirmPasswordTextField.setVisible(isConfirmPasswordFieldNotVisible);
		(isConfirmPasswordFieldNotVisible ? this.confirmPasswordTextField : this.confirmPasswordPasswordField).requestFocus();
	}

	@FXML
	public void passwordTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordPasswordField.setText(this.passwordTextField.getText());
	}

	@FXML
	public void passwordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordTextField.setText(this.passwordPasswordField.getText());
	}

	@FXML
	public void confirmPasswordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
		this.confirmPasswordTextField.setText(this.confirmPasswordPasswordField.getText());
	}

	@FXML
	public void confirmPasswordTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.confirmPasswordPasswordField.setText(this.confirmPasswordTextField.getText());
	}

	@FXML
	public void signupButtonOnAction (ActionEvent actionEvent) {
		// Loop through all required fields and if any field is empty alert it and focus on empty field.
		for (final String key : this.fxmlTextFieldsMap.keySet()) if (this.fxmlTextFieldsMap.get(key).getText().isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "The '" + key + "' Field is empty. Please complete the fields.").show();
			this.fxmlTextFieldsMap.get(key).requestFocus();
			return;
		}

		// If password field is not matching with 'confirm password field', then alert it and focus on currently visible 'confirm password field'.
		if (!this.passwordPasswordField.getText().equals(this.confirmPasswordPasswordField.getText())) {
			new Alert(Alert.AlertType.WARNING, "Password must match with confirm password.").show();
			(this.confirmPasswordPasswordField.isVisible() ? this.confirmPasswordPasswordField : this.confirmPasswordTextField).requestFocus();
			return;
		}

		final User newUser = User.builder().
			userName(this.userNameTextField.getText()).
			initials(this.initialsTextField.getText()).
			firstName(this.firstNameTextField.getText()).
			NIC(this.nicTextField.getText()).
			address(this.addressTextField.getText()).
			DOB(this.dobTextField.getText()).
			role(this.roleTextField.getText()).
			build();

		if (!this.lastNameTextField.getText().isEmpty()) newUser.setLastName(this.lastNameTextField.getText());
		if (!this.emailTextField.getText().isEmpty()) newUser.setEmail(this.emailTextField.getText());
		if (!this.passwordPasswordField.getText().isEmpty()) newUser.setPassword(this.passwordPasswordField.getText());

		final String salaryStr = this.salaryTextField.getText();

		if (!salaryStr.matches("^\\d+(\\.\\d+)?$") || Double.parseDouble(salaryStr) == 0.0) {
			new Alert(Alert.AlertType.WARNING, "Invalid salary value. Please check the salary value and try again.").show();
			this.salaryTextField.requestFocus();
			return;
		}

		if (!this.adminNameTextField.getText().isEmpty()) {
			final int adminID = this.userService.getAdminID(this.adminNameTextField.getText());

			if (adminID != -1) newUser.setAdminID(adminID);
		}

		newUser.setSalary(Double.parseDouble(salaryStr));
		newUser.setType(this.asAdminCheckBox.isSelected() ? "ADMIN" : "EMPLOYEE");

		if (this.userService.add(newUser)) {
			new Alert(Alert.AlertType.INFORMATION, "New user has added. Please login again to continue.").showAndWait();
			this.backButtonOnAction(actionEvent); // Close the current signup window and open login window.
			return;
		}

		new Alert(Alert.AlertType.WARNING, "Failed to add new user. Please click signup button to try again.").show();
	}

	@FXML
	public void backButtonOnAction (ActionEvent actionEvent) {
		try {
			FormController.getInstance().openStage(null, "login_view", "Login - Clothify Store", true); // Open login window.
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close(); // Close current signup window.
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load login window. Please click 'Back' button again.").show();
		}
	}
}
