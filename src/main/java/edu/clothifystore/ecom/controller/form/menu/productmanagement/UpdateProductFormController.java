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

public class UpdateProductFormController implements Initializable, MenuForm {
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
	public TextField productIDTextField;
	@FXML
	public Button deleteButton;
	@FXML
	public Button updateButton;
	@FXML
	public Button imageAddButton;
	@FXML
	public Button imageRemoveButton;

	private File selectedImageFile;
	private final ProductService productService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.PRODUCT);
	private int searchedID = -1;

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

		this.productIDTextField.clear();
		this.productNameTextField.clear();
		this.quantityTextField.clear();
		this.brandTextField.clear();
		this.priceTextField.clear();
		this.descriptionTextField.clear();
		this.typeTextField.clear();

		this.sizeComboBox.setValue(ProductSize.XS);
		this.genderComboBox.setValue(ProductGender.MALE);

		this.deleteButton.setDisable(true);
		this.updateButton.setDisable(true);
		this.toggleInputFieldsDisable(true);

		this.imageView.setImage(new Image(getClass().getResourceAsStream("/" + UserConfig.getConfiguration("default_show_image")))); // Reset loaded image to the default.
	}

	private void toggleInputFieldsDisable (boolean state) {
		this.productNameTextField.setDisable(state);
		this.quantityTextField.setDisable(state);
		this.brandTextField.setDisable(state);
		this.priceTextField.setDisable(state);
		this.descriptionTextField.setDisable(state);
		this.typeTextField.setDisable(state);
		this.sizeComboBox.setDisable(state);
		this.genderComboBox.setDisable(state);
		this.imageAddButton.setDisable(state);
		this.imageRemoveButton.setDisable(state);
	}

	private void clearImage () {
		this.selectedImageFile = null;
		this.imageView.setImage(new Image(getClass().getResourceAsStream("/" + UserConfig.getConfiguration("default_show_image"))));
	}

	private void copyProductToForm (Product product) {
		this.productNameTextField.setText(product.getName());
		this.quantityTextField.setText(product.getQuantity().toString());
		this.brandTextField.setText(product.getBrand());
		this.priceTextField.setText(product.getPrice().toString());
		this.descriptionTextField.setText(product.getDescription());
		this.typeTextField.setText(product.getType());

		this.sizeComboBox.setValue(ProductSize.fromString(product.getSize()));
		this.genderComboBox.setValue(ProductGender.fromString(product.getGender()));

		final String imagePath = String.format("%s%06d.png", UserConfig.getConfiguration("image_save_directory"), product.getId());
		final File imageFile = new File(imagePath);

		if (imageFile.exists()) {
			this.selectedImageFile = imageFile;
			this.imageView.setImage(new Image(imageFile.toURI().toString()));
		} else {
			this.clearImage();
		}

		this.deleteButton.setDisable(false);
		this.updateButton.setDisable(false);
		this.toggleInputFieldsDisable(false);
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
			final Path directoryPath = Path.of(UserConfig.getConfiguration("image_save_directory"));

			if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);

			final Path destinationPath = Path.of(UserConfig.getConfiguration("image_save_directory"), String.format("%06d", id) + fileExtension);
			Files.copy(this.selectedImageFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	private void deleteImage (int id) {
		try {
			final Path directoryPath = Path.of(UserConfig.getConfiguration("image_save_directory"));

			final String filePath = String.format("%06d.png", id);
			final Path imagePath = directoryPath.resolve(filePath);

			if (Files.exists(imagePath)) {
				Files.delete(imagePath);
				System.out.println("Image deleted successfully: " + imagePath);
			} else {
				System.out.println("No image found for deletion: " + imagePath);
			}
		} catch (IOException exception) {
			System.out.println("Failed to delete image: " + exception.getMessage());
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

	@FXML
	public void searchButtonOnAction (ActionEvent actionEvent) {
		final String idStr = this.productIDTextField.getText().trim();
		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);

		if (this.isInvalidateTextField(idStr, "ID", this.productIDTextField, inputValidator::isNonZeroPositiveInteger)) return;

		final int id = Integer.parseInt(idStr);
		final Product searchedProduct = this.productService.get(id);

		if (searchedProduct == null) {
			new Alert(Alert.AlertType.WARNING, "No product has found with this ID: " + id).show();
			this.productIDTextField.requestFocus();
			return;
		}

		this.searchedID = id;

		this.copyProductToForm(searchedProduct);
	}

	@FXML
	public void clearButtonOnAction (ActionEvent actionEvent) {
		this.clearInputData();
	}

	@FXML
	public void deleteButtonOnAction (ActionEvent actionEvent) {
		if (this.searchedID == -1) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Delete product");
		alert.setHeaderText("Delete product confirm.");
		alert.setContentText("Are you sure that you want to delete product?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.productService.delete(this.searchedID)) {
			new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully.").show();
			this.deleteImage(this.searchedID);
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.WARNING, "Product delete failed.").show();
		}
	}

	@FXML
	public void updateButtonOnAction (ActionEvent actionEvent) {
		final Product newProduct = this.validateInputsAndGetNewProduct();

		if (newProduct == null || this.searchedID == -1) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Update product");
		alert.setHeaderText("Update product confirm.");
		alert.setContentText("Are you sure that you want to update product?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		newProduct.setId(this.searchedID);

		if (this.productService.update(newProduct)) {
			new Alert(Alert.AlertType.INFORMATION, "Product updated successfully.").show();

			if (this.selectedImageFile == null) {
				this.deleteImage(this.searchedID);
			} else {
				this.saveSelectedImage(this.searchedID);
			}

			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.WARNING, "Product update failed.").show();
		}
	}

	@FXML
	public void imageRemoveButtonOnAction (ActionEvent actionEvent) {
		this.clearImage();
	}
}
