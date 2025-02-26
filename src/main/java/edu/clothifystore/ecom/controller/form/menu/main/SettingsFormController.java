package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UserConfig;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class SettingsFormController implements Initializable, MenuForm {
	@FXML
	public TextField resourceDirectoryTextField;
	@FXML
	public TextField oldPasswordTextField;
	@FXML
	public TextField newPasswordTextField;
	@FXML
	public TextField confirmPasswordTextField;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.resourceDirectoryTextField.setText(UserConfig.getConfiguration("resources"));
	}

	@Override
	public void update () {}

	@FXML
	public void resourceDirectoryChangeButtonOnAction (ActionEvent actionEvent) {
		if (this.resourceDirectoryTextField.getText().equals(UserConfig.getConfiguration("resources"))) return;

		final String newPath = this.resourceDirectoryTextField.getText();

		if (Paths.get(newPath).isAbsolute() && newPath.matches("^[A-Z]:\\\\.*") && Files.exists(Paths.get(newPath))) {
			UserConfig.setConfiguration("resources", this.resourceDirectoryTextField.getText());
			UserConfig.saveConfig();
			new Alert(Alert.AlertType.INFORMATION, "Path changed.").show();
		} else {
			new Alert(Alert.AlertType.WARNING, "The directory you have given is not exists. No Changes were made.").show();
			this.resourceDirectoryTextField.requestFocus();
		}
	}

	@FXML
	public void resourceDirectoryPickButtonOnAction (ActionEvent actionEvent) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();

		directoryChooser.setTitle("Select Resource Directory");

		final File selectedDirectory = directoryChooser.showDialog(((Button) actionEvent.getSource()).getScene().getWindow());

		if (selectedDirectory != null) resourceDirectoryTextField.setText(selectedDirectory.getAbsolutePath());
	}

	@FXML
	public void openConfigurationButtonOnAction (ActionEvent actionEvent) {
		try {
			final String userHome = System.getProperty("user.home");
			final File configFile = new File(userHome, "Documents/clothify_store.config");

			if (!configFile.exists()) {
				System.out.println("Config file does not exist: " + configFile.getAbsolutePath());
				return;
			}

			Desktop.getDesktop().open(configFile);
			System.out.println("Opened configuration file.");
		} catch (IOException exception) {
			System.err.println("Error opening config file: " + exception.getMessage());
		}
	}

	@FXML
	public void passwordChangeButtonOnAction (ActionEvent actionEvent) {
		final String oldPassword = this.oldPasswordTextField.getText().trim();

		if (!oldPassword.equals(FormController.getInstance().getCurentEmployee().getPassword())) {
			new Alert(Alert.AlertType.WARNING, "Old password is not matching with current password.").show();
			this.oldPasswordTextField.requestFocus();
			return;
		}

		if (newPasswordTextField.getText().length() < 4) {
			new Alert(Alert.AlertType.WARNING, "Password must have at least 4 characters.").show();
			this.newPasswordTextField.requestFocus();
			return;
		}

		if (!newPasswordTextField.getText().equals(this.confirmPasswordTextField.getText())) {
			new Alert(Alert.AlertType.WARNING, "New password and Confirm password must match. Please try again.").show();
			this.confirmPasswordTextField.requestFocus();
			return;
		}

		final Employee currentEmployee = FormController.getInstance().getCurentEmployee();

		currentEmployee.setPassword(this.newPasswordTextField.getText());

		if (((EmployeeService) UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.EMPLOYEE)).update(currentEmployee)) {
			new Alert(Alert.AlertType.INFORMATION, "Password changed! Please login again!").showAndWait();

			try {
				FormController.getInstance().openStage(null, "login_view", "Login - Clothify Store", true);
				((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
			} catch (IOException exception) {
				new Alert(Alert.AlertType.WARNING, "Failed to open login window. Password changed somehow. Next time you login insert new password.").show();
			}
		} else {
			new Alert(Alert.AlertType.WARNING, "Failed to update the password. Try again.").show();
		}
	}

	@FXML
	public void logoutButtonOnAction (ActionEvent actionEvent) {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Logout");
		alert.setHeaderText("Logout confirm.");
		alert.setContentText("You sure want to logout now?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		try {
			FormController.getInstance().openStage(null, "login_view", "Login - Clothify Store", true);
			((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR, "Can't logout. Somthing is wrong");
		}
	}

	@FXML
	public void exitButtonOnAction (ActionEvent actionEvent) {
		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Exit");
		alert.setHeaderText("Exit confirm.");
		alert.setContentText("You sure want to exit now?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		System.exit(0);
	}
}
