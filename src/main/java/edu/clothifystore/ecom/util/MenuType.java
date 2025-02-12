package edu.clothifystore.ecom.util;

public enum MenuType {
	DASHBOARD("main"),
	EMPLOYEE_MANAGEMENT("main"),
	PRODUCT_MANAGEMENT("main"),
	INVENTORY_MANAGEMENT("main"),
	SUPPLIER_MANAGEMENT("main"),
	ORDER_MANAGEMENT("main"),
	REPORTS("main"),
	SETTINGS("main"),
	ADD_EMPLOYEE("employeemanagement"),
	UPDATE_EMPLOYEE("employeemanagement"),
	DELETE_EMPLOYEE("employeemanagement"),
	EMPLOYEE_DETAILS("employeemanagement");

	private final String path;

	MenuType (String path) {
		this.path = path;
	}

	@Override
	public String toString () {
		return this.path + "/" + this.name().toLowerCase();
	}
}
