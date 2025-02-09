package edu.clothifystore.ecom.controller.form;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.util.MenuType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
		final Button dashboardMenuButton = (Button) (((Pane) this.menuPane.getChildren().get(1)).getChildren().getFirst()); // menu pane has two Panes as child. First pane is empty and make shadow. Second has 9 Buttons for all 9 menus. When application load, first menu loading is dashboard. So, among 9 buttons, first button is refer to dashboard menu. Check the FXML structure on 'main_view.fxml' in 'resources/view' directory.
		final User currentUser = FormController.getInstance().getCurentUser();

		FormController.getInstance().setMainContentPane(this.mainContentPane);

		try {
			this.openMenu(MenuType.DASHBOARD, dashboardMenuButton);
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load dashboard. Please open menu and click on another menu and come back to try again.").show();
		}

		this.initTime();

		if (currentUser != null) this.userNameLabel.setText(currentUser.getUserName());
	}

	private void initTime () {
		final int interval = 10;

		final Timeline timeline = new Timeline(
			new KeyFrame(Duration.ZERO, e -> {
				final LocalDateTime now = LocalDateTime.now();

				this.dateLabel.setText(String.format(
					"%d %s %02d",
					now.getYear(),
					now.getMonth().toString().toLowerCase(),
					now.getDayOfMonth()
				));
				this.timeLabel.setText(String.format(
					"%02d:%02d",
					now.getHour(),
					now.getMinute()
				));
			}),
			new KeyFrame(Duration.seconds(interval))
		);

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	// Load new menu into main content pane.
	private void openMenu (MenuType menuType, Button button) throws IOException {
		this.menuPane.setVisible(false); // Always close the menu pane when click on a menu button.

		if (button.equals(this.currentActiveMenuButton)) return; // If target menu is already opened, exit.

		if (this.currentActiveMenuButton != null) this.currentActiveMenuButton.getStyleClass().remove("button-active");

		button.getStyleClass().add("button-active");

		this.currentActiveMenuButton = button;

		if (FormController.getInstance().openMenu(menuType) /* Load new menu. */ == null) { // loaded AnchorPane is null means, 'mainContentPane' is not set into SuperFormController's instance.
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
