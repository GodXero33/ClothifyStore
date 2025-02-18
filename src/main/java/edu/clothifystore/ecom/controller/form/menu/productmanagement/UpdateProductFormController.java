package edu.clothifystore.ecom.controller.form.menu.productmanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.ProductService;
import edu.clothifystore.ecom.util.ProductGender;
import edu.clothifystore.ecom.util.ProductSize;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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

	private File selectedImageFile;
	private final ProductService productService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.PRODUCT);

	@Override
	public void update () {

	}

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {

	}

	public void imageAddButtonOnAction (ActionEvent actionEvent) {
	}

	public void deleteButtonOnAction (ActionEvent actionEvent) {
	}

	public void searchButtonOnAction (ActionEvent actionEvent) {

	}

	public void updateButtonOnAction (ActionEvent actionEvent) {
	}
}
