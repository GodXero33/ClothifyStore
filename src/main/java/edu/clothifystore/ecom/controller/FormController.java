package edu.clothifystore.ecom.controller;

import edu.clothifystore.ecom.Starter;
import edu.clothifystore.ecom.util.MenuType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormController {
	private static FormController instance;

	@Setter
	private AnchorPane mainContentPane;
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

		if (this.loadedStagesMap.get(menuType) == null) {
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
