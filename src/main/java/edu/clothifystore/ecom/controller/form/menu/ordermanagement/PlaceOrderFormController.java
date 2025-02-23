package edu.clothifystore.ecom.controller.form.menu.ordermanagement;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.OrderItem;
import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable, MenuForm {
	@FXML
	public TextField searchProductTextField;
	@FXML
	public ComboBox<String> sizeComboBox;
	@FXML
	public ComboBox<String> genderComboBox;
	@FXML
	public TableView<Product> productsTable;
	@FXML
	public TableColumn<Product, String> productsTableNameColumn;
	@FXML
	public TableColumn<Product, Double> productsTablePriceColumn;
	@FXML
	public TableColumn<Product, String> productsTableSizeColumn;
	@FXML
	public TableColumn<Product, String> productsTableGenderColumn;
	@FXML
	public TableColumn<Product, Integer> productsTableQuantityColumn;
	@FXML
	public TableView<OrderItem> cartTable;
	@FXML
	public TableColumn<OrderItem, String> cartTableNameColumn;
	@FXML
	public TableColumn<OrderItem, Integer> cartTableQuantityColumn;
	@FXML
	public TableColumn<OrderItem, Double> cartTablePriceColumn;
	@FXML
	public TableColumn<OrderItem, Double> cartTableDiscountColumn;
	@FXML
	public Button removeItemButton;
	@FXML
	public Button placeOrderButton;
	@FXML
	public Label cartTotalLabel;
	@FXML
	public TextField tenderAmountTextField;
	@FXML
	public Label changeDueLabel;
	@FXML
	public Label productNameLabel;
	@FXML
	public TextField quantityTextField;
	@FXML
	public TextField discountTextField;
	@FXML
	public Label productGenderLabel;
	@FXML
	public Label productSizeLabel;
	@FXML
	public Button addToCartButton;
	@FXML
	public Label productPriceLabel;
	@FXML
	public Label userNameLabel;

	private final ProductService productService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.PRODUCT);
	private List<Product> productsList;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.sizeComboBox.getItems().setAll("All", "XS", "S", "M", "L", "XL", "XXL");
		this.genderComboBox.getItems().setAll("All", "Male", "Female", "Common");

		this.sizeComboBox.setValue("All");
		this.genderComboBox.setValue("All");

		this.productsTableNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getName()));
		this.productsTablePriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
		this.productsTableSizeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSize()));
		this.productsTableGenderColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGender()));
		this.productsTableQuantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));

		this.cartTableNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProduct().getName()));
		this.cartTableQuantityColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuantity()));
		this.cartTablePriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProduct().getPrice()));
		this.cartTableDiscountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDiscount()));

		this.userNameLabel.setText(FormController.getInstance().getCurentEmployee().getUserName());

		this.loadProducts();
		this.resetForm();
		this.iniEventListeners();
	}

	@Override
	public void update () {
		this.resetForm();
	}

	private void iniEventListeners () {
		this.sizeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> this.filterProductsTable());
		this.genderComboBox.valueProperty().addListener((observable, oldValue, newValue) -> this.filterProductsTable());
		this.searchProductTextField.textProperty().addListener((observable, oldValue, newValue) -> this.filterProductsTable());
		this.quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> this.checkProductDetails());
		this.discountTextField.textProperty().addListener((observable, oldValue, newValue) -> this.checkProductDetails());

		this.productsTable.setRowFactory(tv -> {
			final TableRow<Product> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getClickCount() == 1) this.updateProductDetails(row.getItem());
			});

			return row;
		});

	}

	private void resetForm () {
		this.searchProductTextField.clear();
		this.tenderAmountTextField.clear();

		this.cartTotalLabel.setText("0.0 Rs");
		this.changeDueLabel.setText("0.0 Rs");

		this.sizeComboBox.setValue("All");
		this.genderComboBox.setValue("All");

		this.removeItemButton.setDisable(true);
		this.placeOrderButton.setDisable(true);
		this.addToCartButton.setDisable(true);

		this.clearProductDetails();
		this.filterProductsTable();
	}

	private void filterProductsTable () {
		final List<Product> filteredProductsList = new ArrayList<>();
		final String searchName = this.searchProductTextField.getText().trim();
		final String size = this.sizeComboBox.getValue();
		final String gender = this.genderComboBox.getValue();

		for (final Product product : this.productsList) {
			if (
				(searchName.isEmpty() || product.getName().toLowerCase().contains(searchName.toLowerCase())) &&
				(size == null || size.isEmpty() || size.equals("All") || product.getSize().equalsIgnoreCase(size)) &&
				(gender == null || gender.isEmpty() || gender.equals("All") || product.getGender().equalsIgnoreCase(gender) || product.getGender().equalsIgnoreCase("common"))
			) filteredProductsList.add(product);
		}

		this.clearProductDetails();
		this.productsTable.getSelectionModel().clearSelection();
		this.productsTable.setItems(FXCollections.observableArrayList(filteredProductsList));
	}

	private void loadProducts () {
		this.productsList = this.productService.getAll();

		this.filterProductsTable();
	}

	private void clearProductDetails () {
		this.productNameLabel.setText("");
		this.productGenderLabel.setText("");
		this.productSizeLabel.setText("");
		this.productPriceLabel.setText("0.0 Rs");

		this.quantityTextField.clear();
		this.discountTextField.clear();

		this.quantityTextField.setDisable(true);
		this.discountTextField.setDisable(true);
	}

	private void checkProductDetails () {
		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);
		final String quantity = this.quantityTextField.getText().trim();
		final String discount = this.discountTextField.getText().trim();
		final boolean isInvalidDetails = !inputValidator.isNonZeroPositiveInteger(quantity) || !inputValidator.isNonZeroPositiveDouble(discount);

		this.addToCartButton.setDisable(isInvalidDetails);

		if (isInvalidDetails) {
			this.productPriceLabel.setText("0.0 Rs");
			return;
		}

		final Product selectedProduct = this.productsTable.getSelectionModel().getSelectedItem();

		if (selectedProduct == null) return;

		final double productPrice = selectedProduct.getPrice() * Integer.parseInt(quantity) - Double.parseDouble(discount);

		this.productPriceLabel.setText(productPrice + " Rs");
	}

	private void updateProductDetails (Product product) {
		if (product == null) return;

		this.productNameLabel.setText(product.getName());
		this.productGenderLabel.setText(product.getGender());
		this.productSizeLabel.setText(product.getSize());

		this.quantityTextField.setDisable(false);
		this.discountTextField.setDisable(false);
	}

	@FXML
	public void removeItemButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void placeOrderButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void addToCartButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void clearCartButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void refreshProductButtonOnAction (ActionEvent actionEvent) {
		this.loadProducts();
	}
}
