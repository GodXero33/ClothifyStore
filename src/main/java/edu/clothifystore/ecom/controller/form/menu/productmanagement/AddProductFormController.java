package edu.clothifystore.ecom.controller.form.menu.productmanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AddProductFormController implements Initializable, MenuForm {
	@FXML
	public TextField productNameTextField;
	@FXML
	public ComboBox<ProductSize> sizeComboBox;
	@FXML
	public TextField quantityTextField;
	@FXML
	public TextField brandTextField;
	@FXML
	public ComboBox<ProductGender> genderComboBox;
	@FXML
	public TextField priceTextField;
	@FXML
	public TextField descriptionTextField;
	@FXML
	public TextField typeTextField;
	@FXML
	public ImageView imageView;
	@FXML
	public Button productEditFormActionButton;

	private File selectedImageFile;
	private final ProductService productService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.PRODUCT);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.sizeComboBox.getItems().addAll(new ArrayList<>(Arrays.asList(ProductSize.values())));
		this.genderComboBox.getItems().addAll(new ArrayList<>(Arrays.asList(ProductGender.values())));
		this.sizeComboBox.setValue(ProductSize.XS);
		this.genderComboBox.setValue(ProductGender.MALE);
	}

	@Override
	public void update () {
		this.clearInputData();
	}

	private void clearInputData () {
		this.selectedImageFile = null;

		this.productNameTextField.clear();
		this.quantityTextField.clear();
		this.brandTextField.clear();
		this.priceTextField.clear();
		this.descriptionTextField.clear();
		this.typeTextField.clear();

		this.sizeComboBox.setValue(ProductSize.XS);
		this.genderComboBox.setValue(ProductGender.MALE);

		this.imageView.setImage(new Image(getClass().getResourceAsStream("/" + UserConfig.getConfiguration("default_show_image")))); // Reset loaded image to the default.
	}

	private boolean isInvalidateTextField (String str, String message, TextField textField, Predicate<String> validationCallback) {
		if (str.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Product " + message +" can't be empty.").show();
			textField.requestFocus();
			return true;
		}

		if (validationCallback != null && !validationCallback.test(str)) {
			new Alert(Alert.AlertType.WARNING, "Invalid input for " + message).show();
			textField.requestFocus();
			return true;
		}

		return false;
	}

	private Product validateInputsAndGetNewProduct () {
		final String productName = this.productNameTextField.getText().trim().toLowerCase();
		final String quantity = this.quantityTextField.getText().trim().toLowerCase();
		final String brand = this.brandTextField.getText().trim().toLowerCase();
		final String price = this.priceTextField.getText().trim().toLowerCase();
		final String description = this.descriptionTextField.getText().trim().toLowerCase();
		final String type = this.typeTextField.getText().trim().toLowerCase();
		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);

		if (this.isInvalidateTextField(productName, "name", this.productNameTextField, null)) return null;
		if (this.isInvalidateTextField(quantity, "quantity", this.quantityTextField, inputValidator::isNonZeroPositiveInteger)) return null;
		if (this.isInvalidateTextField(brand, "brand", this.brandTextField, null)) return null;
		if (this.isInvalidateTextField(price, "price", this.priceTextField, inputValidator::isNonZeroPositiveDouble)) return null;
		if (this.isInvalidateTextField(type, "type", this.typeTextField, null)) return null;

		final Product newProduct = Product.builder().
			name(productName).
			size(this.sizeComboBox.getValue().toString()).
			price(Double.parseDouble(price)).
			quantity(Integer.parseInt(quantity)).
			brand(brand).
			gender(this.genderComboBox.getValue().toString()).
			type(type).
			build();

		if (!description.isEmpty()) newProduct.setDescription(description);

		return newProduct;
	}

	private void saveSelectedImage (int id) {
		if (this.selectedImageFile == null) return;

		try {
			String fileExtension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf('.'));
			final Path directoryPath = Path.of(UserConfig.getConfiguration("resources") + "ProductImages/");

			if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);

			final Path destinationPath = Path.of(UserConfig.getConfiguration("resources") + "ProductImages/", String.format("%06d", id) + fileExtension);
			Files.copy(this.selectedImageFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	@FXML
	public void productEditFormActionButtonOnAction (ActionEvent actionEvent) {
		final Product newProduct = this.validateInputsAndGetNewProduct();

		if (newProduct == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Add new product");
		alert.setHeaderText("Add new product confirm.");
		alert.setContentText("Are you sure that you want to add new product?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		final int productID = this.productService.addAndGetID(newProduct);

		if (productID == -1) { // Product add failed.
			new Alert(Alert.AlertType.ERROR, "Failed to add new product.").show();
		} else { // Product added.
			new Alert(Alert.AlertType.INFORMATION, "New product added successfully.").show();
			this.saveSelectedImage(productID);
			this.clearInputData();
		}
	}

	@FXML
	public void imageAddButtonOnAction (ActionEvent actionEvent) {
		final FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Select Product Image");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));

		this.selectedImageFile = fileChooser.showOpenDialog(null);

		if (this.selectedImageFile != null) {
			this.imageView.setImage(new Image(selectedImageFile.toURI().toString()));
		}
	}
}
