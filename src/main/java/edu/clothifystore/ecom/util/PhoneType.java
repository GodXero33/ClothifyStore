package edu.clothifystore.ecom.util;

import lombok.Getter;

@Getter
public enum PhoneType {
	MOBILE("Mobile"), HOME("Home"), WHATSAPP("WhatsApp");

	private final String displayName;

	PhoneType (String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString () {
		return this.displayName;
	}

	public static PhoneType fromString (String str) {
		if (str == null) return null;

		return switch (str.trim().toLowerCase()) {
			case "mobile" -> PhoneType.MOBILE;
			case "home" -> PhoneType.HOME;
			case "whatsapp" -> PhoneType.WHATSAPP;
			default -> null;
		};
	}
}
