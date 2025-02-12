package edu.clothifystore.ecom.controller.form.menu.employeemanagement;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.dto.EmployeePhone;
import edu.clothifystore.ecom.service.ServiceFactory;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.InputValidator;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddEmployeeFormController implements Initializable, MenuForm {
	@FXML
	public TextField userNameTextField;
	@FXML
	public TextField fullNameTextField;
	@FXML
	public TextField nicTextField;
	@FXML
	public TextField emailTextField;
	@FXML
	public TextField addressTextField;
	@FXML
	public ComboBox<String> dobDateComboBox;
	@FXML
	public ComboBox<String> dobMonthComboBox;
	@FXML
	public ComboBox<String> dobYearComboBox;
	@FXML
	public TextField salaryTextField;
	@FXML
	public TextField roleTextField;
	@FXML
	public TextField supervisorUsernameTextField;
	@FXML
	public TextField phone1TextField;
	@FXML
	public ComboBox<String> phone1TypeComboBox;
	@FXML
	public TextField phone2TextField;
	@FXML
	public ComboBox<String> phone2TypeComboBox;
	@FXML
	public TextField phone3TextField;
	@FXML
	public ComboBox<String> phone3TypeComboBox;
	@FXML
	public CheckBox asAdminCheckBox;

	private String dobValue;
	private final EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.EMPLOYEE);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.createDOBComboBoxes();
		this.createPhoneComboBoxes();

		this.dobDateComboBox.setOnAction(event -> this.onDOBChange());
		this.dobMonthComboBox.setOnAction(event -> this.onDOBChange());
		this.dobYearComboBox.setOnAction(event -> this.onDOBChange());
	}

	@Override
	public void update () {
		this.clearInputData();
	}

	private void createDOBComboBoxes () {
		this.dobValue = null;

		final List<String> dates = new ArrayList<>();
		final List<String> months = new ArrayList<>();
		final List<String> years = new ArrayList<>();

		for (int a = 1; a < 32; a++) dates.add(a + "");
		for (int a = 1; a < 13; a++) months.add(a + "");
		for (int a = 2020; a > 1979; a--) years.add(a + "");

		this.dobDateComboBox.getItems().addAll(dates);
		this.dobMonthComboBox.getItems().addAll(months);
		this.dobYearComboBox.getItems().addAll(years);

		this.dobDateComboBox.setValue("");
		this.dobMonthComboBox.setValue("");
		this.dobYearComboBox.setValue("");
	}

	private void createPhoneComboBoxes () {
		final List<String> phoneTypes = new ArrayList<>();

		phoneTypes.add("Mobile");
		phoneTypes.add("Home");
		phoneTypes.add("WhatsApp");

		this.phone1TypeComboBox.getItems().addAll(phoneTypes);
		this.phone2TypeComboBox.getItems().addAll(phoneTypes);
		this.phone3TypeComboBox.getItems().addAll(phoneTypes);

		this.phone1TypeComboBox.setValue("Mobile");
		this.phone2TypeComboBox.setValue("Home");
		this.phone3TypeComboBox.setValue("WhatsApp");
	}

	private void clearInputData () {
		this.dobValue = null;

		this.userNameTextField.clear();
		this.fullNameTextField.clear();
		this.nicTextField.clear();
		this.emailTextField.clear();
		this.addressTextField.clear();
		this.salaryTextField.clear();
		this.roleTextField.clear();
		this.supervisorUsernameTextField.clear();
		this.phone1TextField.clear();
		this.phone2TextField.clear();
		this.phone3TextField.clear();

		this.dobDateComboBox.setValue("");
		this.dobMonthComboBox.setValue("");
		this.dobYearComboBox.setValue("");

		this.phone1TypeComboBox.setValue("Mobile");
		this.phone2TypeComboBox.setValue("Home");
		this.phone3TypeComboBox.setValue("WhatsApp");
	}

	private boolean isLeapYear (int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	private int getValidatedDateUpperBound (int month, int year) {
		return switch (month) {
			case 1, 3, 5, 7, 8, 10, 12 -> 31;
			case 4, 6, 9, 11 -> 30;
			case 2 -> this.isLeapYear(year) ? 29 : 28;
			default -> 1;
		};
	}

	private void invalidDOBComboBoxValue (String message, String defaultValue, ComboBox<String> comboBox) {
		new Alert(Alert.AlertType.WARNING, message).show();
		comboBox.setValue(defaultValue);
		comboBox.requestFocus();
	}

	private void onDOBChange () {
		final String dateStr = this.dobDateComboBox.getValue();
		final String monthStr = this.dobMonthComboBox.getValue();
		final String yearStr = this.dobYearComboBox.getValue();

		if (dateStr.isEmpty() || monthStr.isEmpty() || yearStr.isEmpty()) return; // In case any of combo box is empty.

		if (!dateStr.matches("^\\d+$")) {
			this.invalidDOBComboBoxValue("Given Date is not a number. Please add correct date value.", "1", this.dobDateComboBox);
			return;
		}

		if (!monthStr.matches("^\\d+$")) {
			this.invalidDOBComboBoxValue("Given Month is not a number. Please add correct month value.", "1", this.dobMonthComboBox);
			return;
		}

		if (!yearStr.matches("^\\d+$")) {
			this.invalidDOBComboBoxValue("Given Year is not a number. Please add correct year value.", "2020", this.dobYearComboBox);
			return;
		}

		final int date = Integer.parseInt(dateStr);
		final int month = Integer.parseInt(monthStr);
		final int year = Integer.parseInt(yearStr);

		if (year < 1980 || year > 2030) {
			this.invalidDOBComboBoxValue("Year is invalid. Year must be a value between 1980-2030", "2020", this.dobYearComboBox);
			return;
		}

		if (month < 1 || month > 12) {
			this.invalidDOBComboBoxValue("Month is invalid. Month must be a value between 1-12", "1", this.dobMonthComboBox);
			return;
		}

		final int dateUpperBound = this.getValidatedDateUpperBound(month, year);

		if (date < 1 || date > dateUpperBound) {
			this.invalidDOBComboBoxValue("Date is invalid. Date must be a value between 1-" + dateUpperBound, "1", this.dobDateComboBox);
			return;
		}

		this.dobValue = String.format("%d/%02d/%02d", year, month, date);
	}

	private void invalidInputValueOnEmployeeAdd (String message, Control targetController) {
		new Alert(Alert.AlertType.WARNING, message).show();
		targetController.requestFocus();
	}

	private boolean isInvalidPhoneNumber (int index, String phone, String type, Control targetController, InputValidator inputValidator) {
		if ((type.equals("MOBILE") || type.equals("WHATSAPP"))) {
			if (inputValidator.isMobilePhoneNumber(phone)) return false;

			this.invalidInputValueOnEmployeeAdd("Mobile phone number or whatsapp number must match 07xxxxxxxx or empty in phone number: " + index, targetController);
			return true;
		}

		if (!inputValidator.isHomePhoneNumber(phone)) {
			this.invalidInputValueOnEmployeeAdd("Home phone number must match 0xxxxxxxxx or empty in phone number: " + index, targetController);
			return true;
		}

		return false;
	}

	private boolean isPhoneNumberAlreadyTaken (int index, String phone, Control targetController) {
		if (this.employeeService.isPhoneAvailable(phone)) {
			this.invalidInputValueOnEmployeeAdd("The phone " + index + " (" + phone + ") has already in the system. Can't add again.", targetController);
			return true;
		}

		return false;
	}

	private boolean validatePhoneNumbers (String phone1, String phone2, String phone3, String phone1Type, String phone2Type, String phone3Type, InputValidator inputValidator) {
		if (
			(!phone1.isEmpty() && this.isInvalidPhoneNumber(1, phone1, phone1Type, this.phone1TextField, inputValidator)) ||
			(!phone2.isEmpty() && this.isInvalidPhoneNumber(2, phone2, phone2Type, this.phone2TextField, inputValidator)) ||
			(!phone3.isEmpty() && this.isInvalidPhoneNumber(3, phone3, phone3Type, this.phone3TextField, inputValidator))
		) return false;

		if (
			this.isPhoneNumberAlreadyTaken(1, phone1, this.phone1TextField) ||
			this.isPhoneNumberAlreadyTaken(2, phone2, this.phone2TextField) ||
			this.isPhoneNumberAlreadyTaken(3, phone3, this.phone3TextField)
		) return false;

		if (!phone1.isEmpty() && (phone1.equals(phone2) || phone1.equals(phone3))) {
			this.invalidInputValueOnEmployeeAdd("Two phone numbers can't be the same.", this.phone1TextField);
			return false;
		}

		if (!phone2.isEmpty() && phone2.equals(phone3)) {
			this.invalidInputValueOnEmployeeAdd("Two phone numbers can't be the same.", this.phone2TextField);
			return false;
		}

		return true;
	}

	private Employee validateInputsAndGetNewEmployee () {
		final String userName = this.userNameTextField.getText().trim().toLowerCase();
		final String fullName = this.fullNameTextField.getText().trim().toLowerCase();
		final String nic = this.nicTextField.getText().trim().toLowerCase();
		final String email = this.emailTextField.getText().trim().toLowerCase();
		final String address = this.addressTextField.getText().trim().toLowerCase();
		final String salary = this.salaryTextField.getText().trim();
		final String role = this.roleTextField.getText().trim().toLowerCase();
		final String supervisorUsername = this.supervisorUsernameTextField.getText().trim().toLowerCase();
		final String phone1 = this.phone1TextField.getText().trim();
		final String phone2 = this.phone2TextField.getText().trim();
		final String phone3 = this.phone3TextField.getText().trim();
		final String phone1Type = this.phone1TypeComboBox.getValue().toUpperCase();
		final String phone2Type = this.phone2TypeComboBox.getValue().toUpperCase();
		final String phone3Type = this.phone3TypeComboBox.getValue().toUpperCase();
		final String type = this.asAdminCheckBox.isSelected() ? "ADMIN" : "EMPLOYEE";

		final InputValidator inputValidator = UtilFactory.getInstance().getObject(InputValidator.class);

		if (userName.isEmpty()) {
			this.invalidInputValueOnEmployeeAdd("User name can't be empty.", this.userNameTextField);
			return null;
		}

		if (!inputValidator.isValidUsername(userName)) {
			this.invalidInputValueOnEmployeeAdd(
				"Invalid username!\n" +
					"- Must be 10 characters or less.\n" +
					"- Only letters, numbers, and underscores (_) are allowed.\n" +
					"- Cannot be only numbers.\n" +
					"- Maximum of 4 numbers allowed.\n" +
					"- No spaces or special characters.",
				this.userNameTextField
			);
			return null;
		}

		if (this.employeeService.isUsernameAvailable(userName)) {
			this.invalidInputValueOnEmployeeAdd("The user name (" + userName + ") has already taken. Try another.", this.userNameTextField);
			return null;
		}

		if (fullName.isEmpty()) {
			this.invalidInputValueOnEmployeeAdd("Full name can't be empty.", this.fullNameTextField);
			return null;
		}

		if (nic.isEmpty()) {
			this.invalidInputValueOnEmployeeAdd("NIC can't be empty.", this.nicTextField);
			return null;
		}

		if (!inputValidator.isValidNIC(nic)) {
			this.invalidInputValueOnEmployeeAdd("Invalid NIC format.", this.nicTextField);
			return null;
		}

		if (this.employeeService.isNICAvailable(nic)) {
			this.invalidInputValueOnEmployeeAdd("The NIC (" + nic + ") is already in the system.", this.nicTextField);
			return null;
		}

		if (!email.isEmpty() && !inputValidator.isEmail(email)) {
			this.invalidInputValueOnEmployeeAdd("Invalid email address. Email address must follow 'example@mail.com' pattern or empty.", this.emailTextField);
			return null;
		}

		if (address.isEmpty()) {
			this.invalidInputValueOnEmployeeAdd("Address can't be empty.", this.addressTextField);
			return null;
		}

		if (!salary.isEmpty() && !inputValidator.isNonZeroPositiveDouble(salary)) {
			this.invalidInputValueOnEmployeeAdd("Invalid Salary value. Salary Must be positive non-zero number or empty.", this.salaryTextField);
			return null;
		}

		if (this.dobValue == null) {
			this.invalidInputValueOnEmployeeAdd("Invalid birthday or haven't assigned a date.", this.dobDateComboBox);
			return null;
		}

		if (role.isEmpty()) {
			this.invalidInputValueOnEmployeeAdd("Job Role Can't be empty.", this.roleTextField);
			return null;
		}

		if (!this.validatePhoneNumbers(phone1, phone2, phone3, phone1Type, phone2Type, phone3Type, inputValidator)) return null;

		int supervisorID = -1;

		if (!supervisorUsername.isEmpty()) {
			supervisorID = this.employeeService.getAdminID(supervisorUsername);

			if (supervisorID == -1) {
				this.invalidInputValueOnEmployeeAdd("The supervisor not found with given user name.", this.supervisorUsernameTextField);
				return null;
			}
		}

		final Employee newEmployee = Employee.builder().
			userName(userName).
			fullName(fullName).
			NIC(nic).
			address(address).
			DOB(this.dobValue).
			type(type).
			role(role).
			build();

		if (!email.isEmpty()) newEmployee.setEmail(email);
		if (supervisorID != -1) newEmployee.setAdminID(supervisorID);

		final List<EmployeePhone> phones = new ArrayList<>();

		if (!phone1.isEmpty()) phones.add(EmployeePhone.builder().phone(phone1).type(phone1Type).build());
		if (!phone2.isEmpty()) phones.add(EmployeePhone.builder().phone(phone2).type(phone2Type).build());
		if (!phone3.isEmpty()) phones.add(EmployeePhone.builder().phone(phone3).type(phone3Type).build());

		newEmployee.setSalary(salary.isEmpty() ? 0.0 : Double.parseDouble(salary));
		newEmployee.setPhone(phones);

		return newEmployee;
	}

	@FXML
	public void employeeAddButtonOnAction (ActionEvent actionEvent) {
		final Employee newEmployee = this.validateInputsAndGetNewEmployee();

		if (newEmployee == null) return;

		final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle("Add new employee");
		alert.setHeaderText("Add new employee confirm.");
		alert.setContentText("Are you sure that you want to add new employee?");

		if (alert.showAndWait().get() != ButtonType.OK) return;

		if (this.employeeService.add(newEmployee)) {
			new Alert(Alert.AlertType.INFORMATION, "New employee added successfully.").show();
			this.clearInputData();
		} else {
			new Alert(Alert.AlertType.ERROR, "Failed to add new employee.").show();
		}
	}
}
