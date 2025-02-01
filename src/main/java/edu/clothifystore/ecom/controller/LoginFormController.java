package edu.clothifystore.ecom.controller;

import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.UserService;
import edu.clothifystore.ecom.util.ServiceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

		if (this.loadedUser == null || !this.loadedUser.getUserName().equals(userName)) this.loadedUser = this.userService.get(userName);

		if (this.loadedUser == null) {
			new Alert(Alert.AlertType.WARNING, "The user with user name '" + userName + "' is not found. Please try again with different user name.").show();
			return;
		}

		if (this.loadedUser.getPassword().equals(this.passwordPasswordField.getText())) {
			try {
				SuperFormController.openStage(this.getClass().getResource("../../../../view/main_view.fxml"), "Clothify Store", true);
				((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
			} catch (IOException exception) {
				new Alert(Alert.AlertType.ERROR, "Failed to load application window. Please click 'Login' button again.").show();
			}
		} else {
			new Alert(Alert.AlertType.WARNING, "The password is incorrect. Please try again.").show();
		}
	}

	@FXML
	public void signupButtonOnAction (ActionEvent actionEvent) {
		try {
			SuperFormController.openStage(this.getClass().getResource("../../../../view/signup_view.fxml"), "Signup - Clothify Store", true);
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load signup window. Please click 'Signup' button again.").show();
		}
	}

	@FXML
	public void showPasswordCheckBoxOnAction (ActionEvent actionEvent) {
		final boolean isPasswordFieldNotVisible = ((CheckBox) actionEvent.getTarget()).isSelected();

		this.passwordPasswordField.setVisible(!isPasswordFieldNotVisible);
		this.passwordTextField.setVisible(isPasswordFieldNotVisible);
		(isPasswordFieldNotVisible ? this.passwordTextField : this.passwordPasswordField).requestFocus();
	}

	@FXML
	public void passwordTextFieldOnKeyReleased (KeyEvent keyEvent) {
		this.loadedUser = null;

		this.passwordPasswordField.setText(this.passwordTextField.getText());
	}

	@FXML
	public void passwordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
		this.loadedUser = null;

		this.passwordTextField.setText(this.passwordPasswordField.getText());
	}
}
