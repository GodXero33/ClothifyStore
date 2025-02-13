package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.MenuType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementFormController implements Initializable, MenuForm {
	@FXML
	public AnchorPane contentPane;
	private Button currentActiveMenuButton;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		final Button addEmployeeMenuButton = (Button) (((Pane) ((Pane) this.contentPane.getParent()).getChildren().getFirst()).getChildren().getFirst()); // content pane's parent is root anchor pane. Root anchor pane has two child anchor panes. One is content pane and other is hold buttons. So, from content pane, go parent anchor pane and get first child gives the anchor pane where buttons are holds. From that anchor pane add employee button is the first one.

		try {
			this.openMenu(MenuType.ADD_EMPLOYEE, addEmployeeMenuButton);
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load dashboard. Please open menu and click on another menu and come back to try again.").show();
		}
	}

	// Load new menu into content pane.
	private void openMenu (MenuType menuType, Button button) throws IOException {
		if (button.equals(this.currentActiveMenuButton)) return; // If target menu is already opened, exit.

		if (this.currentActiveMenuButton != null) this.currentActiveMenuButton.getStyleClass().remove("button-active");

		button.getStyleClass().add("button-active");

		this.currentActiveMenuButton = button;
		final FXMLLoader loader = FormController.getInstance().openMenu(menuType, this.contentPane); // Load new menu.

		if (loader == null) {
			new Alert(Alert.AlertType.ERROR, "Can't load target menu.").show();
		} else {
			((MenuForm) loader.getController()).update();
		}
	}

	@Override
	public void update () {

	}

	@FXML
	public void addEmployeeButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.ADD_EMPLOYEE, (Button) actionEvent.getTarget());
	}

	@FXML
	public void updateEmployeeButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.UPDATE_EMPLOYEE, (Button) actionEvent.getTarget());
	}

	@FXML
	public void deleteEmployeeButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.DELETE_EMPLOYEE, (Button) actionEvent.getTarget());
	}
}
