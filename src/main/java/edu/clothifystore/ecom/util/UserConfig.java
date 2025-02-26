package edu.clothifystore.ecom.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserConfig {
	private static final Map<String, String> configMap = new HashMap<>();

	public static String getConfiguration (String configuration) {
		if (UserConfig.configMap.containsKey(configuration)) return UserConfig.configMap.get(configuration);
		return null;
	}

	public static void setConfiguration (String configuration, String value) {
		if (UserConfig.configMap.containsKey(configuration)) UserConfig.configMap.put(configuration, value);
	}

	public static void loadConfigurations () {
		final String userHome = System.getProperty("user.home");
		final File configFile = new File(userHome, "Documents/clothify_store.config");

		if (configFile.exists()) {
			System.out.println("Config file found: " + configFile.getAbsolutePath());
			UserConfig.readConfig(configFile);
		} else {
			System.out.println("Config file not found. Creating a new one at: " + configFile.getAbsolutePath());
			UserConfig.createDefaultConfig(configFile);
		}
	}

	private static void generateConfigMap (List<String> content) {
		UserConfig.configMap.clear();

		content.forEach(line -> {
			final String[] pieces = line.split("=");

			UserConfig.configMap.put(pieces[0], pieces[1]);
		});
	}

	private static void readConfig (File configFile) {
		try {
			final List<String> lines = Files.readAllLines(configFile.toPath());

			UserConfig.generateConfigMap(lines);
			lines.forEach(System.out::println);
		} catch (IOException exception) {
			System.err.println("Error reading config file: " + exception.getMessage());
		}
	}

	public static void saveConfig () {
		try {
			final String userHome = System.getProperty("user.home");
			final File configFile = new File(userHome, "Documents/clothify_store.config");

			if (configFile.getParentFile() != null) {
				configFile.getParentFile().mkdirs();
			}

			if (!configFile.exists()) {
				configFile.createNewFile();
			}

			final StringBuilder defaultContentBuilder = new StringBuilder();

			UserConfig.configMap.forEach((key, value) -> defaultContentBuilder.append(key).append('=').append(value).append('\n'));

			Files.write(configFile.toPath(), defaultContentBuilder.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
			System.out.println("Config file updated.");
		} catch (IOException exception) {
			System.err.println("Error updating config file: " + exception.getMessage());
		}
	}

	private static void createDefaultConfig (File configFile) {
		try {
			if (configFile.getParentFile() != null) {
				configFile.getParentFile().mkdirs();
			}

			if (configFile.createNewFile()) {
				final List<String> defaultContent = new ArrayList<>();
				final StringBuilder defaultContentBuilder = new StringBuilder();

				defaultContent.add("resources=C:/ClothifyStore/");

				defaultContent.forEach(line -> defaultContentBuilder.append(line).append('\n'));

				Files.write(configFile.toPath(), defaultContentBuilder.toString().getBytes(), StandardOpenOption.WRITE);
				UserConfig.generateConfigMap(defaultContent);
				System.out.println("Default config file created.");
			} else {
				System.out.println("Failed to create config file.");
			}
		} catch (IOException exception) {
			System.err.println("Error creating config file: " + exception.getMessage());
		}
	}
}
