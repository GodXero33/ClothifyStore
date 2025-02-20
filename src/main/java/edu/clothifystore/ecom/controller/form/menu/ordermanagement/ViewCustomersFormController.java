package edu.clothifystore.ecom.controller.form.menu.ordermanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.CustomerService;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomersFormController implements Initializable, MenuForm {
	private static final int MAXIMUM_RECORDS_PER_VIEW = 10;

	@FXML
	public TableView<Customer> customersTable;
	@FXML
	public TableColumn<Customer, Integer> customersTableIDColumn;
	@FXML
	public TableColumn<Customer, String> customersTableNameColumn;
	@FXML
	public TableColumn<Customer, String> customersTablePhoneColumn;
	@FXML
	public TableColumn<Customer, String> customersTableEmailColumn;
	@FXML
	public TableColumn<Customer, String> customersTableAddressColumn;
	@FXML
	public TextField searchNameTextField;
	@FXML
	public Button prevButton;
	@FXML
	public Button nextButton;

	private final CustomerService customerService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.CUSTOMER);
	private int currentPage = 0;
	private List<Customer> customerList;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.customersTableIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
		this.customersTableNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
		this.customersTablePhoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone()));
		this.customersTableEmailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
		this.customersTableAddressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAddress()));

		this.searchNameTextField.textProperty().addListener((observable, oldValue, newValue) -> this.updateTable());

		loadSuppliers();
	}

	@Override
	public void update () {
		this.currentPage = 0;

		this.updateTable();
	}

	private void loadSuppliers () {
		this.customerList = this.customerService.getAll();
		this.currentPage = 0;

		this.updateTable();
	}

	private void updateTable () {
		final String searchName = this.searchNameTextField.getText().trim();
		final List<Customer> filteredList = new ArrayList<>();
		final boolean isEmpty = searchName.isEmpty();

		for (final Customer customer : this.customerList) {
			if (customer.getName().toLowerCase().contains(searchName.toLowerCase()) || isEmpty) {
				filteredList.add(customer);
			}
		}

		final int start = this.currentPage * ViewCustomersFormController.MAXIMUM_RECORDS_PER_VIEW;
		final int end = Math.min(start + ViewCustomersFormController.MAXIMUM_RECORDS_PER_VIEW, filteredList.size());
		final ObservableList<Customer> pageSuppliers = FXCollections.observableArrayList(filteredList.subList(start, end));

		this.customersTable.setItems(pageSuppliers);

		this.prevButton.setDisable(this.currentPage == 0);
		this.nextButton.setDisable(end >= filteredList.size());
	}


	@FXML
	public void prevButtonOnAction (ActionEvent actionEvent) {
		if (this.currentPage > 0) {
			this.currentPage--;
			this.updateTable();
		}
	}

	@FXML
	public void nextButtonOnAction (ActionEvent actionEvent) {
		if ((this.currentPage + 1) * ViewCustomersFormController.MAXIMUM_RECORDS_PER_VIEW < this.customerList.size()) {
			this.currentPage++;
			this.updateTable();
		}
	}

	@FXML
	public void refreshButtonOnAction (ActionEvent actionEvent) {
		this.loadSuppliers();
	}
}
