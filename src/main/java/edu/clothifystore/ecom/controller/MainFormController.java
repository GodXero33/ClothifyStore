package edu.clothifystore.ecom.controller;

import edu.clothifystore.ecom.util.MenuType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
	@FXML
	public AnchorPane mainContentPane;
	@FXML
	public Label dateLabel;
	@FXML
	public Label timeLabel;
	@FXML
	public Label userNameLabel;
	@FXML
	public AnchorPane menuPane;

	private Button currentActiveMenuButton;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.currentActiveMenuButton = (Button) (((Pane) this.menuPane.getChildren().get(1)).getChildren().getFirst()); // menu pane has two Panes as child. First pane is empty and make shadow. Second has 9 Buttons for all 9 menus. When application load, first menu loading is dashboard. So, among 9 buttons, first button is refer to dashboard menu. Check the FXML structure on 'main_view.fxml' in 'resources/view' directory.

		SuperFormController.getInstance().setMainContentPane(this.mainContentPane);
	}

	// Load new menu into main content pane.
	private void openMenu (MenuType menuType, Button button) throws IOException {
		if (button.equals(this.currentActiveMenuButton)) return; // If target menu is already opened, exit.

		this.currentActiveMenuButton = button;

		if (SuperFormController.getInstance().openMenu(menuType) /* Load new menu. */ == null) { // loaded AnchorPane is null means, 'mainContentPane' is not set into SuperFormController's instance.
			new Alert(Alert.AlertType.ERROR, "Can't load target menu. May be Main  content is not loaded. May be restart application will resolve the problem.").show();
		}
	}

	@FXML
	public void menuButtonOnAction (ActionEvent actionEvent) {
		this.menuPane.setVisible(!this.menuPane.isVisible());
	}

	@FXML
	public void dashboardButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.DASHBOARD, (Button) actionEvent.getTarget());
	}

	@FXML
	public void userManagementButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.USER_MANAGEMENT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void productManagementButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.PRODUCT_MANAGEMENT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void inventoryManagementButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.INVENTORY_MANAGEMENT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void supplierManagementButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.SUPPLIER_MANAGEMENT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void employeeManagementButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.EMPLOYEE_MANAGEMENT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void orderManagementButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.ORDER_MANAGEMENT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void reportsButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.REPORTS, (Button) actionEvent.getTarget());
	}

	@FXML
	public void settingsButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.SETTINGS, (Button) actionEvent.getTarget());
	}
}
