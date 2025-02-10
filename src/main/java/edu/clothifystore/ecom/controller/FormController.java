package edu.clothifystore.ecom.controller;

import edu.clothifystore.ecom.Starter;
import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.util.MenuType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * All common methods that required to form controller classes are implemented here.
 * This is a singleton class. The constructor is hidden, only a one instance can be made.
 * Authored by GodXero :)
 */
public class FormController {
	private static FormController instance;

	@Setter
	private AnchorPane mainContentPane;
	@Setter
	@Getter
	private User curentUser;
	private final Map<MenuType, FXMLLoader> loadedStagesMap;

	private FormController() {
		this.loadedStagesMap = new HashMap<>();

		this.loadedStagesMap.put(MenuType.DASHBOARD, null);
		this.loadedStagesMap.put(MenuType.USER_MANAGEMENT, null);
		this.loadedStagesMap.put(MenuType.PRODUCT_MANAGEMENT, null);
		this.loadedStagesMap.put(MenuType.INVENTORY_MANAGEMENT, null);
		this.loadedStagesMap.put(MenuType.SUPPLIER_MANAGEMENT, null);
		this.loadedStagesMap.put(MenuType.EMPLOYEE_MANAGEMENT, null);
		this.loadedStagesMap.put(MenuType.ORDER_MANAGEMENT, null);
		this.loadedStagesMap.put(MenuType.REPORTS, null);
		this.loadedStagesMap.put(MenuType.SETTINGS, null);
	}

	public static FormController getInstance () {
		if (FormController.instance == null) FormController.instance = new FormController();

		return FormController.instance;
	}

	public FXMLLoader openStage (Stage stage, String url, String title, boolean show) throws IOException {
		if (stage == null) stage = new Stage();

		final FXMLLoader loader = new FXMLLoader(Starter.class.getResource(String.format("../../../view/%s.fxml", url)));
		stage.setScene(new Scene(loader.load()));
		stage.setTitle(title);
		stage.setResizable(false);

		if (show) stage.show();

		return loader;
	}

	public FXMLLoader openMenu (MenuType menuType) throws IOException {
		if (this.mainContentPane == null) return null;

		final AnchorPane newContent;
		FXMLLoader loader = null;

		if (this.loadedStagesMap.get(menuType) == null) { // If loaded stages map's target value is null, means the stage haven't loaded before. So, load the target stage and store in the map.
			loader = new FXMLLoader(Starter.class.getResource(String.format("../../../view/menu/%s.fxml", menuType.toString().toLowerCase())));
			newContent = loader.load();

			this.loadedStagesMap.put(menuType, loader);
		} else {
			loader = this.loadedStagesMap.get(menuType);
			newContent = loader.getRoot();
		}

		this.mainContentPane.getChildren().setAll(newContent);

		return loader;
	}
}
