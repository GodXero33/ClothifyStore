package edu.clothifystore.ecom.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupFormController {
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
	public Label asAdminCheckBox;

	@FXML
	public void passwordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
	}

	@FXML
	public void showPasswordCheckBoxOnAction (ActionEvent actionEvent) {

	}

	@FXML
	public void passwordTextFieldOnKeyReleased (KeyEvent keyEvent) {
	}

	@FXML
	public void showConfirmPasswordCheckBoxOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void confirmPasswordPasswordFieldOnKeyReleased (KeyEvent keyEvent) {
	}

	@FXML
	public void confirmPasswordTextFieldOnKeyReleased (KeyEvent keyEvent) {
	}

	@FXML
	public void signupButtonOnAction (ActionEvent actionEvent) {
		this.backButtonOnAction(actionEvent);
	}

	@FXML
	public void backButtonOnAction (ActionEvent actionEvent) {
		try {
			SuperFormController.openStage(this.getClass().getResource("../../../../view/login_view.fxml"), "Login - Clothify Store", true);
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load login window. Please click 'Back' button again.").show();
		}
	}
}
