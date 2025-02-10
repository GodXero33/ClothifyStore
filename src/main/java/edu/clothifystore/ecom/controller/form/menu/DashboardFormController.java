package edu.clothifystore.ecom.controller.form.menu;

import edu.clothifystore.ecom.controller.form.MenuForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable, MenuForm {
	@FXML
	public Label todayTotalSalesLabel;
	@FXML
	public Label todayBestProductLabel;
	@FXML
	public Label todayTotalOrdersLabel;
	@FXML
	public Label lastWeekTotalSalesLabel;
	@FXML
	public Label lastWeekBestProductLabel;
	@FXML
	public Label lastWeekTotalOrdersLabel;
	@FXML
	public Label lastMonthTotalSalesLabel;
	@FXML
	public Label lastMonthBestProductLabel;
	@FXML
	public Label lastMonthTotalOrdersLabel;
	@FXML
	public Label totalProductLabel;
	@FXML
	public Label totalEmployeesLabel;
	@FXML
	public Label totalSuppliersLabel;
	@FXML
	public Label totalOrdersLabel;
	@FXML
	public Label alertDisplayLabel;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.update();
	}

	@Override
	public void update () {
		System.out.println(true);
	}
}
