package edu.clothifystore.ecom.util;

public class InputValidator {
	public boolean isNonZeroPositiveInteger (String str) {
		return str.matches("^\\d+$") && Integer.parseInt(str) != 0;
	}

	public boolean isInteger (String str) {
		return str.matches("^-?\\d+$");
	}

	public boolean isNonZeroPositiveDouble (String str) {
		return str.matches("^\\d+(\\.\\d+)?$") && Double.parseDouble(str) != 0.0;
	}

	public boolean isDouble (String str) {
		return str.matches("^-?\\d+(\\.\\d+)?$");
	}

	public boolean isEmail (String str) {
		return str.matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$");
	}

	public boolean isMobilePhoneNumber (String str) {
		return str.matches("0?7([0-2]|[5-8])\\d{7}");
	}

	public boolean isHomePhoneNumber (String str) {
		return str.matches("0?(11|2[1-7]|3[1-8]|41|45|47|5([1-2]|[4-7])|63|6[5-7]|81|91)\\d{7}");
	}

	public boolean isValidUsername (String str) {
		return str.matches("^(?=[a-zA-Z_]*\\d{0,4}[a-zA-Z_])[a-zA-Z0-9_]{1,10}$");
	}

	public boolean isValidNIC (String str) {
		return str.matches("^(?:\\d{2}(?:[0-3]\\d{2}|[5-8]\\d{2})\\d{3}\\d[VvXx]|\\d{4}(?:[0-3]\\d{2}|[5-8]\\d{2})\\d{4}\\d)$");
	}
}
