package edu.clothifystore.ecom.util;

import lombok.Getter;

public enum MenuType {
	DASHBOARD("main", "Dashboard"),
	EMPLOYEE_MANAGEMENT("main", "Employees"),
	PRODUCT_MANAGEMENT("main", "Products"),
	SUPPLIER_MANAGEMENT("main", "Suppliers"),
	ORDER_MANAGEMENT("main", "Orders"),
	REPORTS("main", "Reports"),
	SETTINGS("main", "Settings"),
	ADD_EMPLOYEE("employeemanagement", null),
	EDIT_EMPLOYEE("employeemanagement", null),
	DELETE_EMPLOYEE("employeemanagement", null),
	ADD_PRODUCT("productmanagement", null),
	UPDATE_PRODUCT("productmanagement", null),
	DELETE_PRODUCT("productmanagement", null);

	private final String path;
	@Getter
	private final String displayName;

	MenuType (String path, String displayName) {
		this.path = path;
		this.displayName = displayName;
	}

	@Override
	public String toString () {
		return this.path + "/" + this.name().toLowerCase();
	}
}
