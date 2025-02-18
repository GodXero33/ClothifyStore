package edu.clothifystore.ecom;

import edu.clothifystore.ecom.util.UserConfig;

public class Main {
	public static void main (String[] args) {
		UserConfig.loadConfigurations();
		Starter.main(args);
	}
}
