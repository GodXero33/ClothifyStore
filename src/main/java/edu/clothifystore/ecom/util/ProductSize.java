package edu.clothifystore.ecom.util;

public enum ProductSize {
	XS, S, M, L, XL, XXL;

	public static ProductSize fromString (String str) {
		if (str == null) return null;

		return switch (str.trim().toLowerCase()) {
			case "xs" -> ProductSize.XS;
			case "s" -> ProductSize.S;
			case "m" -> ProductSize.M;
			case "l" -> ProductSize.L;
			case "xl" -> ProductSize.XL;
			case "xxl" -> ProductSize.XXL;
			default -> null;
		};
	}
}
