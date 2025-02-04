package edu.clothifystore.ecom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {
	public static void main (String[] args) {
		Application.launch(args);
	}

	@Override
	public void start (Stage stage) throws Exception {
		stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../../../view/main_view.fxml"))));
		stage.setTitle("Login - Clothify Store");
		stage.show();
	}
}
