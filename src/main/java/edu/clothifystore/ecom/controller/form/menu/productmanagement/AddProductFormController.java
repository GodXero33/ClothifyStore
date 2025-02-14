package edu.clothifystore.ecom.controller.form.menu.productmanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable, MenuForm {
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

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {

	}

	@Override
	public void update () {

	}

	@FXML
	public void productEditFormActionButtonOnAction (ActionEvent actionEvent) {
	}

	@FXML
	public void imageAddButtonOnAction (ActionEvent actionEvent) {
	}
}
