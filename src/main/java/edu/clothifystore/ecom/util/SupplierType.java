package edu.clothifystore.ecom.util;

public enum SupplierType {
	BUSINESS, INDIVIDUAL;

	public static SupplierType fromString (String str) {
		if (str == null) return null;

		return switch (str.trim().toLowerCase()) {
			case "business" -> SupplierType.BUSINESS;
			case "individual" -> SupplierType.INDIVIDUAL;
			default -> null;
		};
	}
}
