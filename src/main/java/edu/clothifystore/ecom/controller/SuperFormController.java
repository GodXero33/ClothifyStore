package edu.clothifystore.ecom.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SuperFormController {
	public static void openStage (URL resource, String title, boolean show) throws IOException {
		final Stage stage = new Stage();

		stage.setScene(new Scene(FXMLLoader.load(resource)));
		stage.setTitle(title);

		if (show) stage.show();
	}
}
