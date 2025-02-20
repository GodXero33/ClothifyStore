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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable, MenuForm {
	@FXML
	public TextField nameTextField;
	@FXML
	public TextField phoneTextField;
	@FXML
	public TextField emailTextField;
	@FXML
	public TextField addressTextField;

	private final CustomerService customerService = UtilFactory.getObject(ServiceFactory.class).getServiceType(ServiceType.CUSTOMER);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {}

	@Override
	public void update () {
		this.clearInputData();
	}

	private void clearInputData () {
		this.nameTextField.clear();
		this.phoneTextField.clear();
		this.emailTextField.clear();
		this.addressTextField.clear();
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

		if (this.customerService.isPhoneAvailable(phone)) {
			new Alert(Alert.AlertType.WARNING, "The phone number has already taken.").show();
			this.phoneTextField.requestFocus();
			return null;
		}

		if (this.customerService.isEmailAvailable(email)) {
			new Alert(Alert.AlertType.WARNING, "The email address has already taken.").show();
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
	public void addButtonOnAction (ActionEvent actionEvent) {
		final Customer newCustomer = this.validateInputsAndGetNewCustomer();

		if (newCustomer == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Add new customer");
		alert.setHeaderText("Add new customer confirm.");
		alert.setContentText("Are you sure that you want to add new customer?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		final int newCustomerID = this.customerService.add(newCustomer);

		if (newCustomerID > 0) {
			new Alert(Alert.AlertType.INFORMATION, "New customer has added.\nNew customer's ID is: " + newCustomerID).show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.WARNING, "Failed to add new customer.").show();
		}
	}

	@FXML
	public void clearButtonOnAction (ActionEvent actionEvent) {
		this.clearInputData();
	}
}
