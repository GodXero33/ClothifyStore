package edu.clothifystore.ecom.controller.form.menu.ordermanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.CustomerService;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerFormController implements Initializable, MenuForm {
	@FXML
	public TextField nameTextField;
	@FXML
	public TextField phoneTextField;
	@FXML
	public TextField emailTextField;
	@FXML
	public TextField addressTextField;
	@FXML
	public Button updateButton;
	@FXML
	public Button deleteButton;

	private final CustomerService customerService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.CUSTOMER);
	private Customer loadedCustomer;

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.toggleInputsDisable(true);
	}

	@Override
	public void update () {
		this.clearInputData();
	}

	private void toggleInputsDisable (boolean state) {
		this.nameTextField.setDisable(state);
		this.addressTextField.setDisable(state);
		this.updateButton.setDisable(state);
		this.deleteButton.setDisable(state);
	}

	private void clearInputData () {
		this.nameTextField.clear();
		this.phoneTextField.clear();
		this.emailTextField.clear();
		this.addressTextField.clear();

		this.toggleInputsDisable(true);
	}

	private void onCustomerLoaded () {
		if (this.loadedCustomer == null) return;

		this.nameTextField.setText(this.loadedCustomer.getName());
		this.phoneTextField.setText(this.loadedCustomer.getPhone());
		this.emailTextField.setText(this.loadedCustomer.getEmail());
		this.addressTextField.setText(this.loadedCustomer.getAddress());

		this.toggleInputsDisable(false);
	}

	private Customer validateInputsAndGetNewCustomer () {
		final String name = this.nameTextField.getText().trim();
		final String phone = this.phoneTextField.getText().trim();
		final String email = this.emailTextField.getText().trim().toLowerCase();
		final String address = this.addressTextField.getText().trim();
		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);

		if (name.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Name can't be empty.").show();
			this.nameTextField.requestFocus();
			return null;
		}

		if (!phone.isEmpty() && !inputValidator.isHomePhoneNumber(phone) && !inputValidator.isMobilePhoneNumber(phone)) {
			new Alert(Alert.AlertType.WARNING, "Invalid phone number.").show();
			this.phoneTextField.requestFocus();
			return null;
		}

		if (!email.isEmpty() && !inputValidator.isEmail(email)) {
			new Alert(Alert.AlertType.WARNING, "Invalid email address.").show();
			this.emailTextField.requestFocus();
			return null;
		}

		return Customer.builder().
			name(name).
			phone(phone.isEmpty() ? null : phone).
			email(email.isEmpty() ? null : email).
			address(address.isEmpty() ? null : address).
			build();
	}

	@FXML
	public void updateButtonOnAction (ActionEvent actionEvent) {
		if (this.loadedCustomer == null) return;

		final Customer newCustomer = this.validateInputsAndGetNewCustomer();

		if (newCustomer == null) return;

		newCustomer.setId(this.loadedCustomer.getId());

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Update customer");
		alert.setHeaderText("Update customer confirm.");
		alert.setContentText("Are you sure that you want to update customer?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.customerService.update(newCustomer)) {
			new Alert(Alert.AlertType.INFORMATION, "Customer has updated.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.WARNING, "Failed to update customer.").show();
		}
	}

	@FXML
	public void deleteButtonOnAction (ActionEvent actionEvent) {
		if (this.loadedCustomer == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Delete customer");
		alert.setHeaderText("Delete customer confirm.");
		alert.setContentText("Are you sure that you want to delete customer?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.customerService.delete(this.loadedCustomer.getId())) {
			new Alert(Alert.AlertType.INFORMATION, "Customer has deleted.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.WARNING, "Failed to delete customer.").show();
		}
	}

	@FXML
	public void clearButtonOnAction (ActionEvent actionEvent) {
		this.clearInputData();
	}

	public void searchButtonOnAction (ActionEvent actionEvent) {
		final String phone = this.phoneTextField.getText().trim();
		final String email = this.emailTextField.getText().trim().toLowerCase();
		final InputValidator inputValidator = UtilFactory.getObject(InputValidator.class);

		if (phone.isEmpty() && email.isEmpty()) {
			new Alert(Alert.AlertType.WARNING, "Please add value to phone or email fields before search.").show();
			this.phoneTextField.requestFocus();
			return;
		}

		if (inputValidator.isHomePhoneNumber(phone) || inputValidator.isMobilePhoneNumber(phone)) {
			this.loadedCustomer = this.customerService.getByPhone(phone);

			if (this.loadedCustomer != null) {
				new Alert(Alert.AlertType.INFORMATION, "Customer has found. Please update or delete the customer.").show();
				this.onCustomerLoaded();
				return;
			}
		}

		if (inputValidator.isEmail(email)) {
			this.loadedCustomer = this.customerService.getByEmail(email);

			if (this.loadedCustomer != null) {
				new Alert(Alert.AlertType.INFORMATION, "Customer has found. Please update or delete the customer.").show();
				this.onCustomerLoaded();
				return;
			}
		}

		new Alert(Alert.AlertType.INFORMATION, "No customer has found with given data.").show();
		(phone.isEmpty() ? this.emailTextField : this.phoneTextField).requestFocus();
	}
}
