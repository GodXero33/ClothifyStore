package edu.clothifystore.ecom.controller.form;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.UserService;
import edu.clothifystore.ecom.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
	@FXML
	public TextField userNameTextField;
	@FXML
	public TextField passwordTextField;
	@FXML
	public PasswordField passwordPasswordField;

	private final UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);
	private User loadedUser;

	@FXML
	public void loginButtonOnAction (ActionEvent actionEvent) {
		final String userName = this.userNameTextField.getText();

		if (userName.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "The user name is empty. Please first insert the user name to login.").show();
			return;
		}

		// If loaded user data is empty load user data from database.
		if (this.loadedUser == null) this.loadedUser = this.userService.get(userName);

		// If loaded user data still empty after loaded, that means user is not found.
		if (this.loadedUser == null) {
			new Alert(Alert.AlertType.WARNING, "The user with user name '" + userName + "' is not found. Please try again with different user name.").show();
			return;
		}

		// Check password in loaded data and password field text is equals.
		if (this.loadedUser.getPassword().equals(this.passwordPasswordField.getText())) { // If both strings are equal, login is success.
			try {
				FormController.getInstance().openStage(null, "main_view", "Clothify Store", true); // Open main window.
				((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close(); // Close current login window.
			} catch (IOException exception) {
				new Alert(Alert.AlertType.ERROR, "Failed to load application window. Please click 'Login' button again.").show();
			}
		} else { // If both strings are not equal, login failed.
			new Alert(Alert.AlertType.WARNING, "The password is incorrect. Please try again.").show();
		}
	}

	@FXML
	public void signupButtonOnAction (ActionEvent actionEvent) {
		try {
			FormController.getInstance().openStage(null, "signup_view", "Signup - Clothify Store", true); // Open signup window.
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close(); // Close current login window.
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load signup window. Please click 'Signup' button again.").show();
		}
	}

	// Toggle visibility between password 'password field' and password 'text field' and focus visibility enabled field.
	@FXML
	public void showPasswordCheckBoxOnAction (ActionEvent actionEvent) {
		final boolean isPasswordFieldNotVisible = ((CheckBox) actionEvent.getTarget()).isSelected();

		this.passwordPasswordField.setVisible(!isPasswordFieldNotVisible);
		this.passwordTextField.setVisible(isPasswordFieldNotVisible);
		(isPasswordFieldNotVisible ? this.passwordTextField : this.passwordPasswordField).requestFocus();
	}

	@FXML
	public void passwordTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordPasswordField.setText(this.passwordTextField.getText());
	}

	@FXML
	public void passwordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
		this.passwordTextField.setText(this.passwordPasswordField.getText());
	}

	// If user changed username text field text delete loaded user data.
	@FXML
	public void userNameTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.loadedUser = null;
	}
}
