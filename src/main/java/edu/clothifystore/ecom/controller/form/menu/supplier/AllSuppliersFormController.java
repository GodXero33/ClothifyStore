package edu.clothifystore.ecom.controller.form.menu.supplier;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.SupplierService;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllSuppliersFormController implements Initializable, MenuForm {
	private static final int MAXIMUM_RECORDS_PER_VIEW = 10;

	@FXML
	public TableView<Supplier> suppliersTable;
	@FXML
	public TableColumn<Supplier, Integer> suppliersTableIDColumn;
	@FXML
	public TableColumn<Supplier, String> suppliersTableNameColumn;
	@FXML
	public TableColumn<Supplier, String> suppliersTablePhoneColumn;
	@FXML
	public TableColumn<Supplier, String> suppliersTableEmailColumn;
	@FXML
	public TableColumn<Supplier, String> suppliersTableTypeColumn;
	@FXML
	public Button prevButton;
	@FXML
	public Button nextButton;

	private final SupplierService supplierService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.SUPPLIER);
	private int currentPage = 0;
	private List<Supplier> allSuppliers = new ArrayList<>();

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.suppliersTableIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
		this.suppliersTableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		this.suppliersTablePhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone()));
		this.suppliersTableEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
		this.suppliersTableTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));

		this.loadSuppliers();
	}

	@Override
	public void update () {
		this.loadSuppliers();
	}

	private void loadSuppliers () {
		this.currentPage = 0;
		this.allSuppliers = this.supplierService.getAll();

		this.updateTable();
	}

	private void updateTable () {
		final int start = this.currentPage * AllSuppliersFormController.MAXIMUM_RECORDS_PER_VIEW;
		final int end = Math.min(start + AllSuppliersFormController.MAXIMUM_RECORDS_PER_VIEW, this.allSuppliers.size());

		final ObservableList<Supplier> pageSuppliers = FXCollections.observableArrayList(this.allSuppliers.subList(start, end));
		this.suppliersTable.setItems(pageSuppliers);

		this.prevButton.setDisable(this.currentPage == 0);
		this.nextButton.setDisable(end >= this.allSuppliers.size());
	}


	@FXML
	public void refreshButtonOnAction (ActionEvent actionEvent) {
		this.loadSuppliers();
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
		if ((this.currentPage + 1) * AllSuppliersFormController.MAXIMUM_RECORDS_PER_VIEW < this.allSuppliers.size()) {
			this.currentPage++;
			this.updateTable();
		}
	}
}
