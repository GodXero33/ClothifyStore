package edu.clothifystore.ecom;

import edu.clothifystore.ecom.controller.FormController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application {
	public static void main (String[] args) {
		Application.launch(args);
	}

	@Override
	public void start (Stage stage) throws Exception {
		FormController.getInstance().openStage(stage, "main_view", "Login - Clothify Store", false);
		stage.setResizable(false);
		stage.show();
	}
}
