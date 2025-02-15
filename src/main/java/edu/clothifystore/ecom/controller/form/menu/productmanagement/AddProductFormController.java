package edu.clothifystore.ecom.controller.form.menu.productmanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable, MenuForm {
	private static final String IMAGE_SAVE_DIRECTORY = "C:/ClothifyStore/ProductImages/";

	@FXML
	public TextField productNameTextField;
	@FXML
	public ComboBox<String> sizeComboBox;
	@FXML
	public TextField quantityTextField;
	@FXML
	public TextField brandTextField;
	@FXML
	public ComboBox<String> genderComboBox;
	@FXML
	public TextField priceTextField;
	@FXML
	public TextField descriptionTextField;
	@FXML
	public ComboBox<String> typeComboBox;
	@FXML
	public ImageView imageView;
	@FXML
	public Button productEditFormActionButton;

	private File selectedImageFile;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {

	}

	@Override
	public void update () {

	}

	private void saveSelectedImage () {
		if (this.selectedImageFile == null) return;

		try {
			String fileExtension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf('.'));
			final Path directoryPath = Path.of(AddProductFormController.IMAGE_SAVE_DIRECTORY);

			if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);

			final Path destinationPath = Path.of(AddProductFormController.IMAGE_SAVE_DIRECTORY, "001" + fileExtension);
			Files.copy(this.selectedImageFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		}
	}

	@FXML
	public void productEditFormActionButtonOnAction (ActionEvent actionEvent) {
		this.saveSelectedImage();
	}

	@FXML
	public void imageAddButtonOnAction (ActionEvent actionEvent) {
		final FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Select Product Image");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

		this.selectedImageFile = fileChooser.showOpenDialog(null);

		if (this.selectedImageFile != null) {
			this.imageView.setImage(new Image(selectedImageFile.toURI().toString()));
		}
	}
}
