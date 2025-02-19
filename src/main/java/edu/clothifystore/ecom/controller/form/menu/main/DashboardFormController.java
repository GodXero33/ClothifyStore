package edu.clothifystore.ecom.controller.form.menu.main;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.service.custom.OrderService;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.service.custom.SupplierService;
import edu.clothifystore.ecom.util.DailyAlertGenerator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
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

	private final EmployeeService employeeService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.EMPLOYEE);
	private final ProductService productService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.PRODUCT);
	private final SupplierService supplierService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.SUPPLIER);
	private final OrderService orderService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.ORDER);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.update();
		new DailyAlertGenerator(this.alertDisplayLabel, FormController.getInstance().getCurentEmployee().getUserName());
	}

	@Override
	public void update () {
		final int totalEmployees = this.employeeService.getCount();
		final int totalProduct = this.productService.getCount();
		final int totalSuppliers = this.supplierService.getCount();
		final int totalOrders = this.orderService.getCount();

		this.totalEmployeesLabel.setText(totalEmployees == -1 ? "0" : totalEmployees + "");
		this.totalProductLabel.setText(totalProduct == -1 ? "0" : totalProduct + "");
		this.totalSuppliersLabel.setText(totalSuppliers == -1 ? "0" : totalSuppliers + "");
		this.totalOrdersLabel.setText(totalOrders == -1 ? "0" : totalOrders + "");
	}
}
