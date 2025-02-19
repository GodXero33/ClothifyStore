package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable, MenuForm {
	@FXML
	public AnchorPane contentPane;
	private Button currentActiveMenuButton;

	private final EmployeeService employeeService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.EMPLOYEE);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		final Button employeeSalaryReportButton = (Button) (((Pane) ((Pane) this.contentPane.getParent()).getChildren().getFirst()).getChildren().getFirst());

		try {
			this.openMenu(MenuType.EMPLOYEE_SALARY_REPORT, employeeSalaryReportButton);
		} catch (IOException exception) {
			new Alert(Alert.AlertType.ERROR, "Failed to load employee salary report menu. Please open menu and click on another menu and come back to try again.").show();
		}
	}

	@Override
	public void update () {}

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
	public void dailyEmployeeReportOnAction (ActionEvent actionEvent) {

	}

	@FXML
	public void employeeSalaryReportButtonOnAction (ActionEvent actionEvent) throws IOException {
		this.openMenu(MenuType.EMPLOYEE_SALARY_REPORT, (Button) actionEvent.getTarget());
	}

	@FXML
	public void allEmployeesReportButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void suppliersReportButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void ordersReportButtonOnAction (ActionEvent actionEvent) {
	}
}
