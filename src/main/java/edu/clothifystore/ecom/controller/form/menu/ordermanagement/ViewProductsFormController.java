package edu.clothifystore.ecom.controller.form.menu.ordermanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewProductsFormController implements Initializable, MenuForm {
	private static final int MAXIMUM_RECORDS_PER_VIEW = 10;

	@FXML
	public TableView<Product> productsTable;
	@FXML
	public TableColumn<Product, Integer> productsTableIDColumn;
	@FXML
	public TableColumn<Product, String> productsTableNameColumn;
	@FXML
	public TableColumn<Product, String> productsTableSizeColumn;
	@FXML
	public TableColumn<Product, Double> productsTablePriceColumn;
	@FXML
	public TableColumn<Product, Double> productsTableDiscountTable;
	@FXML
	public TableColumn<Product, Integer> productsTableQuantityTable;
	@FXML
	public TableColumn<Product, String> productsTableBrandColumn;
	@FXML
	public TableColumn<Product, String> productsTableGenderColumn;
	@FXML
	public TableColumn<Product, String> productsTableTypeColumn;
	@FXML
	public TextField searchNameTextField;
	@FXML
	public Button prevButton;
	@FXML
	public Button nextButton;
	@FXML
	public ComboBox<String> sizeComboBox;
	@FXML
	public ComboBox<String> genderComboBox;

	private final ProductService productService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.PRODUCT);
	private int currentPage = 0;
	private List<Product> productList;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.productsTableIDColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
		this.productsTableNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
		this.productsTableSizeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSize()));
		this.productsTablePriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
		this.productsTableDiscountTable.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDiscount()));
		this.productsTableQuantityTable.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
		this.productsTableBrandColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBrand()));
		this.productsTableGenderColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGender()));
		this.productsTableTypeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType()));

		this.sizeComboBox.getItems().setAll("ALL", "XS", "S", "M", "L", "XL", "XXL");
		this.genderComboBox.getItems().setAll("ALL", "MALE", "FEMALE", "COMMON");

		this.sizeComboBox.setValue("ALL");
		this.genderComboBox.setValue("ALL");

		this.sizeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> this.updateTable());
		this.genderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> this.updateTable());

		this.searchNameTextField.textProperty().addListener((observable, oldValue, newValue) -> this.updateTable());

		this.loadProducts();
	}

	@Override
	public void update () {
		this.currentPage = 0;

		this.updateTable();
	}

	private void loadProducts () {
		this.productList = this.productService.getAll();
		this.currentPage = 0;

		this.updateTable();
	}

	private void updateTable () {
		final String searchName = this.searchNameTextField.getText().trim();
		final List<Product> filteredList = new ArrayList<>();
		final String size = this.sizeComboBox.getValue();
		final String gender = this.genderComboBox.getValue();

		for (final Product product : this.productList) {
			if (
				(searchName.isEmpty() || product.getName().toLowerCase().contains(searchName.toLowerCase())) &&
				(size == null || size.isEmpty() || size.equals("ALL") || product.getSize().equalsIgnoreCase(size)) &&
				(gender == null || gender.isEmpty() || gender.equals("ALL") || product.getGender().equalsIgnoreCase(gender))
			) {
				filteredList.add(product);
			}
		}

		final int start = this.currentPage * ViewProductsFormController.MAXIMUM_RECORDS_PER_VIEW;
		final int end = Math.min(start + ViewProductsFormController.MAXIMUM_RECORDS_PER_VIEW, filteredList.size());
		final ObservableList<Product> pageSuppliers = FXCollections.observableArrayList(filteredList.subList(start, end));

		this.productsTable.setItems(pageSuppliers);

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
		if ((this.currentPage + 1) * ViewProductsFormController.MAXIMUM_RECORDS_PER_VIEW < this.productList.size()) {
			this.currentPage++;
			this.updateTable();
		}
	}

	@FXML
	public void refreshButtonOnAction (ActionEvent actionEvent) {
		this.loadProducts();
	}
}
