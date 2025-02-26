package edu.clothifystore.ecom.controller.form.menu.ordermanagement;

import edu.clothifystore.ecom.controller.FormController;
import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Order;
import edu.clothifystore.ecom.dto.OrderItem;
import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.OrderService;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.util.ReportGenerator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

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
	private final OrderService orderService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.ORDER);
	private List<Product> productsList;
	private final List<OrderItem> orderItemsList = new ArrayList<>();

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
		this.cartTablePriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProduct().getPrice() * cellData.getValue().getQuantity()));
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
		this.tenderAmountTextField.textProperty().addListener((observable, oldValue, newValue) -> this.updateCartTable());

		this.productsTable.setRowFactory(tv -> {
			final TableRow<Product> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getClickCount() == 1) this.updateProductDetailsByProductTable(row.getItem());
			});

			return row;
		});

		this.cartTable.setRowFactory(tv -> {
			final TableRow<OrderItem> row = new TableRow<>();

			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getClickCount() == 1) this.updateProductDetailsByCartTable(row.getItem());
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
		final Product selectedProduct = this.productsTable.getSelectionModel().getSelectedItem();

		if (selectedProduct == null) return;

		final String quantity = this.quantityTextField.getText().trim();
		final String discount = this.discountTextField.getText().trim();
		final boolean isInvalidDetails = !quantity.matches("^\\d+$") || Integer.parseInt(quantity) == 0 || Integer.parseInt(quantity) > selectedProduct.getQuantity() || !discount.matches("^(\\d+(\\.\\d+)?)?$");

		this.addToCartButton.setDisable(isInvalidDetails);

		if (isInvalidDetails) {
			this.productPriceLabel.setText("0.0 Rs");
			return;
		}

		this.productPriceLabel.setText(selectedProduct.getPrice() * Integer.parseInt(quantity) - (discount.isEmpty() ? 0.0 : Double.parseDouble(discount)) + " Rs");
	}

	private void updateProductDetailsByProductTable (Product product) {
		if (product == null) return;

		this.productNameLabel.setText(product.getName());
		this.productGenderLabel.setText(product.getGender());
		this.productSizeLabel.setText(product.getSize());

		this.quantityTextField.setDisable(false);
		this.discountTextField.setDisable(false);
		this.quantityTextField.requestFocus();

		this.checkProductDetails();
	}

	private void updateProductDetailsByCartTable (OrderItem orderItem) {
		if (orderItem == null) {
			this.removeItemButton.setDisable(true);
			return;
		}

		this.removeItemButton.setDisable(false);
	}

	private void updateCartTable () {
		final List<OrderItem> orderItems = this.orderItemsList.stream().toList();

		this.cartTable.setItems(FXCollections.observableArrayList(orderItems));
		this.cartTable.refresh();

		double cartTotal = 0;

		for (final OrderItem orderItem : orderItems)
			cartTotal += orderItem.getQuantity() * orderItem.getProduct().getPrice() - orderItem.getDiscount();

		this.cartTotalLabel.setText(cartTotal + " Rs");

		final String tenderAmount = this.tenderAmountTextField.getText();

		if (orderItemsList.isEmpty() || !tenderAmount.matches("^\\d+(\\.\\d+)?$") || Double.parseDouble(tenderAmount) == 0.0 || cartTotal > Double.parseDouble(tenderAmount)) {
			this.changeDueLabel.setText("0.0 Rs");
			this.placeOrderButton.setDisable(true);
			return;
		}

		this.changeDueLabel.setText(Double.parseDouble(tenderAmount) - cartTotal + " Rs");
		this.placeOrderButton.setDisable(false);
	}

	private void restoreProductStock (Product product, Integer quantity) {
		for (final Product productCheck : this.productsList) {
			if (productCheck.getId().equals(product.getId())) {
				productCheck.setQuantity(productCheck.getQuantity() + quantity);
				this.filterProductsTable();
				this.productsTable.refresh();
				break;
			}
		}
	}

	private void clearCartTable (boolean restoreStock) {
		if (restoreStock)
			this.orderItemsList.forEach(orderItem -> this.restoreProductStock(orderItem.getProduct(), orderItem.getQuantity()));

		this.orderItemsList.clear();
		this.updateCartTable();
		this.tenderAmountTextField.clear();
	}

	@FXML
	public void removeItemButtonOnAction (ActionEvent actionEvent) {
		final OrderItem selectedOrderItem = this.cartTable.getSelectionModel().getSelectedItem();

		if (selectedOrderItem == null) return;

		for (final OrderItem orderItem : this.orderItemsList) {
			if (orderItem.getProduct().getId().equals(selectedOrderItem.getProduct().getId())) {
				this.orderItemsList.remove(orderItem);
				this.restoreProductStock(orderItem.getProduct(), orderItem.getQuantity());
				this.updateCartTable();
				break;
			}
		}
	}

	@FXML
	public void placeOrderButtonOnAction (ActionEvent actionEvent) {
		final LocalDateTime now = LocalDateTime.now();
		final String date = String.format("%d/%02d/%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		final String time = String.format("%02d:%02d", now.getHour(), now.getMinute());
		final String tenderAmountStr = this.tenderAmountTextField.getText();
		final double tenderAmount = tenderAmountStr.matches("^\\d+(\\.\\d+)?$") ? Double.parseDouble(tenderAmountStr) : 0.0;
		final Order newOrder = Order.builder().
			date(date).
			time(time).
			employeeID(FormController.getInstance().getCurentEmployee().getId()).
			orderItems(this.orderItemsList).
			build();

		final int newOrderID = this.orderService.add(newOrder);

		if (newOrderID != 0) {
			this.clearCartTable(false);
			this.clearProductDetails();

			final Map<String, Object> parameters = new HashMap<>();

			parameters.put("ORDER_ID", newOrderID);
			parameters.put("TENDER_AMOUNT", tenderAmount);

			UtilFactory.getObject(ReportGenerator.class).startGenerateReport("order_recipt", "order/receipt/", "receipt", parameters);

			return;
		}

		new Alert(Alert.AlertType.WARNING, "Failed to place the order. Try again.").show();
	}

	@FXML
	public void addToCartButtonOnAction (ActionEvent actionEvent) {
		final Product selectedProduct = this.productsTable.getSelectionModel().getSelectedItem();
		final String quantity = this.quantityTextField.getText().trim();
		final String discount = this.discountTextField.getText().trim();
		final int quantityValue = Integer.parseInt(quantity);
		final double discountValue = discount.isEmpty() ? 0.0 : Double.parseDouble(discount);

		selectedProduct.setQuantity(selectedProduct.getQuantity() - quantityValue);
		this.productsTable.refresh();
		this.clearProductDetails();

		for (final OrderItem orderItem : this.orderItemsList) {
			if (Objects.equals(orderItem.getProduct().getId(), selectedProduct.getId())) {
				orderItem.setQuantity(orderItem.getQuantity() + quantityValue);
				orderItem.setDiscount(discountValue);
				this.updateCartTable();

				return;
			}
		}

		this.orderItemsList.add(OrderItem.builder().
			product(selectedProduct).
			quantity(quantityValue).
			discount(discountValue).
			build());

		this.updateCartTable();
	}

	@FXML
	public void clearCartButtonOnAction (ActionEvent actionEvent) {
		this.clearCartTable(true);
	}

	@FXML
	public void refreshProductButtonOnAction (ActionEvent actionEvent) {
		this.loadProducts();
	}
}
