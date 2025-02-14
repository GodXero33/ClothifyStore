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

public class ProductManagementFormController implements Initializable, MenuForm {
	@FXML
	public AnchorPane contentPane;

	private Button currentActiveMenuButton;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		final Button addProductMenuButton = (Button) (((Pane) ((Pane) this.contentPane.getParent()).getChildren().getFirst()).getChildren().getFirst());

		try {
			this.openMenu(MenuType.ADD_PRODUCT, addProductMenuButton);
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load add product menu. Please open menu and click on another menu and come back to try again.").show();
		}
	}

	@Override
	public void update () {

	}

	// Load new menu into content pane.
	private void openMenu (MenuType menuType, Button button) throws IOException {
		if (button.equals(this.currentActiveMenuButton)) return; // If target menu is already opened, exit.

		final FXMLLoader loader = FormController.getInstance().openMenu(menuType, this.contentPane); // Load new menu.

		if (loader == null) {
			new Alert(Alert.AlertType.ERROR, "Can't load target menu.").show();
			return;
		}

		if (this.currentActiveMenuButton != null) this.currentActiveMenuButton.getStyleClass().remove("button-active");

		this.currentActiveMenuButton = button;

		button.getStyleClass().add("button-active");
		((MenuForm) loader.getController()).update();
	}

	@FXML
	public void addProductButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.ADD_PRODUCT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void editProductButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.UPDATE_PRODUCT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void deleteProductButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.DELETE_PRODUCT, (Button) actionEvent.getTarget());
	}
}
