package edu.clothifystore.ecom.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

public class MainFormController {
	public AnchorPane navPanel;

	private boolean isNavPanelOpen;

	public void navPanelOpenButtonOnAction (ActionEvent actionEvent) {
		this.navPanel.setPrefWidth(this.isNavPanelOpen ? 65 : 200);
		this.isNavPanelOpen = !this.isNavPanelOpen;
	}
}
