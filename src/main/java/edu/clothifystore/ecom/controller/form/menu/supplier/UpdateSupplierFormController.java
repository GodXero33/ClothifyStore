package edu.clothifystore.ecom.controller.form.menu.supplier;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.SupplierService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.SupplierType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateSupplierFormController implements Initializable, MenuForm {
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
	public ComboBox<SupplierType> typeComboBox;
	@FXML
	public Button deleteButton;
	@FXML
	public Button updateButton;

	private final SupplierService supplierService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.SUPPLIER);
	private Supplier loadedSupplier;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.typeComboBox.getItems().addAll(SupplierType.values());
		this.typeComboBox.setValue(SupplierType.BUSINESS);

		this.toggleInputsDisable(true);
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
		this.typeComboBox.setValue(SupplierType.BUSINESS);

		this.toggleInputsDisable(true);

		this.loadedSupplier = null;
	}

	private void toggleInputsDisable (boolean state) {
		this.emailTextField.setDisable(state);
		this.addressTextField.setDisable(state);
		this.descriptionTextField.setDisable(state);
		this.typeComboBox.setDisable(state);
		this.deleteButton.setDisable(state);
		this.updateButton.setDisable(state);
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
		final SupplierType type = this.typeComboBox.getValue();

		if (isInputFieldEmpty(name, this.supplierNameTextField, "Name")) return null;
		if (isInputFieldEmpty(phone, this.phoneTextField, "Phone")) return null;
		if (isInputFieldEmpty(address, this.addressTextField, "Address")) return null;

		if (type == null) {
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
			description(description.isEmpty() ? null : description).
			type(type.name()).
			build();
	}

	private void newSupplierOnSearched () {
		if (this.loadedSupplier == null) return;

		this.toggleInputsDisable(false);
		new Alert(Alert.AlertType.INFORMATION, "Supplier has found. Please do the changes.").show();

		this.supplierNameTextField.setText(this.loadedSupplier.getName());
		this.phoneTextField.setText(this.loadedSupplier.getPhone());
		this.emailTextField.setText(this.loadedSupplier.getEmail());
		this.addressTextField.setText(this.loadedSupplier.getAddress());
		this.descriptionTextField.setText(this.loadedSupplier.getDescription());

		this.typeComboBox.setValue(SupplierType.fromString(this.loadedSupplier.getType()));
	}

	@FXML
	public void updateButtonOnAction (ActionEvent actionEvent) {
		final Supplier newSupplier = this.validateInputsNadGetNewSupplier();

		if (newSupplier == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Update supplier");
		alert.setHeaderText("Update supplier confirm.");
		alert.setContentText("Are you sure that you want to update supplier?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		newSupplier.setId(this.loadedSupplier.getId());

		if (this.supplierService.update(newSupplier)) {
			new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to update supplier.").show();
		}
	}

	@FXML
	public void deleteButtonOnAction (ActionEvent actionEvent) {
		if (this.loadedSupplier == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Delete supplier");
		alert.setHeaderText("Delete supplier confirm.");
		alert.setContentText("Are you sure that you want to delete supplier?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.supplierService.delete(this.loadedSupplier.getId())) {
			new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.").show();
		}
	}

	@FXML
	public void clearButtonOnAction (ActionEvent actionEvent) {
		this.clearInputData();
	}

	@FXML
	public void searchButtonOnAction (ActionEvent actionEvent) {
		final String name = this.supplierNameTextField.getText().trim();
		final String phone = this.phoneTextField.getText().trim();

		if (name.isEmpty() && phone.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Please fill name or phone fields before search.").show();
			this.supplierNameTextField.requestFocus();
			return;
		}

		if (!name.isEmpty()) {
			this.loadedSupplier = this.supplierService.getByName(name);

			if (this.loadedSupplier != null) {
				this.newSupplierOnSearched();
				return;
			}
		}

		if (!phone.isEmpty()) {
			this.loadedSupplier = this.supplierService.getByPhone(phone);

			if (this.loadedSupplier != null) {
				this.newSupplierOnSearched();
				return;
			}
		}

		new Alert(Alert.AlertType.WARNING, "No supplier has found with given data.").show();
		this.supplierNameTextField.requestFocus();
	}
}
