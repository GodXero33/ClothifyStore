package edu.clothifystore.ecom.controller;

import edu.clothifystore.ecom.Starter;
import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.util.MenuType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
	@Getter
	private Employee curentEmployee;
	private final Map<MenuType, FXMLLoader> loadedStagesMap;
	private final String formFXMLPath = "../../../view";

	private FormController () {
		this.loadedStagesMap = new HashMap<>();
	}

	public static FormController getInstance () {
		if (FormController.instance == null) FormController.instance = new FormController();

		return FormController.instance;
	}

	public void openStage (Stage stage, String url, String title, boolean show) throws IOException {
		if (stage == null) stage = new Stage();

		final FXMLLoader loader = new FXMLLoader(Starter.class.getResource(String.format("%s/%s.fxml", this.formFXMLPath, url)));
		stage.setScene(new Scene(loader.load()));
		stage.setTitle(title);
		stage.setResizable(false);

		if (show) stage.show();

	}

	public FXMLLoader openMenu (MenuType menuType, Pane pane) throws IOException {
		if (pane == null) return null;

		final AnchorPane newContent;
		FXMLLoader loader;

		if (!this.loadedStagesMap.containsKey(menuType) || this.loadedStagesMap.get(menuType) == null) { // If loaded stages map's target value is null, means the stage haven't loaded before. So, load the target stage and store in the map.
			loader = new FXMLLoader(Starter.class.getResource(String.format("%s/menu/%s.fxml", this.formFXMLPath, menuType.toString())));
			newContent = loader.load();

			this.loadedStagesMap.put(menuType, loader);
		} else {
			loader = this.loadedStagesMap.get(menuType);
			newContent = loader.getRoot();
		}

		pane.getChildren().setAll(newContent);

		return loader;
	}
}
