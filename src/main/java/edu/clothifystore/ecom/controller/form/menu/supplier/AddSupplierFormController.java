package edu.clothifystore.ecom.controller.form.menu.supplier;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.SupplierService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSupplierFormController implements Initializable, MenuForm {
	@FXML
	public TextField supplierNameTextField;
	@FXML
	public TextField phoneTextField;
	@FXML
	public TextField emailTextField;
	@FXML
	public TextField addressTextField;
	@FXML
	public TextField descriptionTextField;
	@FXML
	public ComboBox<String> typeComboBox;

	private final SupplierService supplierService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.SUPPLIER);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.typeComboBox.getItems().addAll("Business", "Individual");
		this.typeComboBox.setValue("Business");
	}

	@Override
	public void update() {
		this.clearInputData();
	}

	private void clearInputData () {
		this.supplierNameTextField.clear();
		this.phoneTextField.clear();
		this.emailTextField.clear();
		this.addressTextField.clear();
		this.descriptionTextField.clear();
		this.typeComboBox.setValue("Business");
	}

	private boolean isInputFieldEmpty (String fieldValue, Control control, String fieldName) {
		if (fieldValue.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, fieldName + " can't be empty.").show();

			if (control != null) control.requestFocus();

			return true;
		}

		return false;
	}

	private Supplier validateInputsNadGetNewSupplier () {
		final String name = this.supplierNameTextField.getText().trim();
		final String phone = this.phoneTextField.getText().trim();
		final String email = this.emailTextField.getText().trim().toLowerCase();
		final String address = this.addressTextField.getText().trim();
		final String description = this.descriptionTextField.getText().trim();
		final String type = this.typeComboBox.getValue();

		if (isInputFieldEmpty(name, this.supplierNameTextField, "Name")) return null;
		if (isInputFieldEmpty(phone, this.phoneTextField, "Phone")) return null;
		if (isInputFieldEmpty(address, this.addressTextField, "Address")) return null;

		if (type == null || type.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Type can't be empty.").show();
			this.typeComboBox.requestFocus();
			return null;
		}

		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);

		if (!inputValidator.isMobilePhoneNumber(phone) && !inputValidator.isHomePhoneNumber(phone)) {
			new Alert(Alert.AlertType.WARNING, "Invalid phone format.").show();
			this.phoneTextField.requestFocus();
			return null;
		}

		if (!email.isEmpty() && !inputValidator.isEmail(email)) {
			new Alert(Alert.AlertType.WARNING, "Invalid email format.").show();
			this.emailTextField.requestFocus();
			return null;
		}

		return Supplier.builder().
			name(name).
			phone(phone).
			email(email.isEmpty() ? null : email).
			address(address.isEmpty() ? null : address).
			address(description.isEmpty() ? null : description).
			type(type).
			build();
	}

	@FXML
	public void supplierAddButtonOnAction(ActionEvent actionEvent) {
		final Supplier newSupplier = this.validateInputsNadGetNewSupplier();

		if (newSupplier == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Add new supplier");
		alert.setHeaderText("Add new supplier confirm.");
		alert.setContentText("Are you sure that you want to add new supplier?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.supplierService.add(newSupplier)) {
			new Alert(Alert.AlertType.INFORMATION, "New supplier added successfully.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to add new supplier.").show();
		}
	}

	@FXML
	public void clearButtonOnAction (ActionEvent actionEvent) {
		this.clearInputData();
	}
}
