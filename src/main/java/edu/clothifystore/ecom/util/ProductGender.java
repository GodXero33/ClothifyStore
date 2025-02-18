package edu.clothifystore.ecom.util;

public enum ProductGender {
	MALE, FEMALE, COMMON;

	public static ProductGender fromString (String str) {
		if (str == null) return null;

		return switch (str.trim().toLowerCase()) {
			case "male" -> ProductGender.MALE;
			case "female" -> ProductGender.FEMALE;
			case "common" -> ProductGender.COMMON;
			default -> null;
		};
	}
}
