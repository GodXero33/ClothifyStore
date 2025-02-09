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
	private final Map<MenuType, AnchorPane> loadedStagesMap;

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

	public Stage openStage (Stage stage, String url, String title, boolean show) throws IOException {
		if (stage == null) stage = new Stage();

		stage.setScene(new Scene(FXMLLoader.load(Starter.class.getResource(String.format("../../../view/%s.fxml", url)))));
		stage.setTitle(title);

		if (show) stage.show();

		return stage;
	}

	public AnchorPane openMenu (MenuType menuType) throws IOException {
		if (this.mainContentPane == null) return null;

		final AnchorPane newContent;

		if (this.loadedStagesMap.get(menuType) == null) { // If loaded stages map's target value is null, means the stage haven't loaded before. So, load the target stage and store in the map.
			String m = menuType.toString();
			newContent = FXMLLoader.load(Starter.class.getResource(String.format("../../../view/menu/%s.fxml", menuType.toString().toLowerCase())));

			this.loadedStagesMap.put(menuType, newContent);
		} else {
			newContent = this.loadedStagesMap.get(menuType);
		}

		this.mainContentPane.getChildren().setAll(newContent);

		return newContent;
	}
}
