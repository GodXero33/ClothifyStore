package edu.clothifystore.ecom.controller.form.menu.report;

import edu.clothifystore.ecom.controller.form.menu.MenuForm;
import edu.clothifystore.ecom.util.ReportGenerator;
import edu.clothifystore.ecom.util.UtilFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class OrdersReportFormController implements Initializable, MenuForm {
	@FXML
	public TextField monthTextField;
	@FXML
	public TextField yearTextField;
	@FXML
	public Slider monthSlider;
	@FXML
	public Slider yearSlider;

	private final ReportGenerator reportGenerator = UtilFactory.getObject(ReportGenerator.class);

	@Override
	public void initialize (URL url, ResourceBundle resourceBundle) {
		this.monthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("\\d*")) {
				final int newValueValue = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);

				if (newValueValue <= this.monthSlider.getMax() && newValueValue >= this.monthSlider.getMin()) {
					this.monthSlider.setValue(newValueValue);
					return;
				}
			}

			this.monthTextField.setText(oldValue);
		});

		this.yearTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("\\d*")) {
				final int newValueValue = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);

				if (newValueValue <= this.yearSlider.getMax() && newValueValue >= this.yearSlider.getMin()) {
					this.yearSlider.setValue(newValueValue);
					return;
				}
			}

			this.yearTextField.setText(oldValue);
		});

		this.monthSlider.valueProperty().addListener((observable, oldValue, newValue) -> this.monthTextField.setText(String.valueOf(newValue.intValue())));
		this.yearSlider.valueProperty().addListener((observable, oldValue, newValue) -> this.yearTextField.setText(String.valueOf(newValue.intValue())));
	}

	@Override
	public void update () {
		this.clearInputs();
	}

	private void clearInputs () {
		this.monthSlider.setValue(0);
		this.yearSlider.setValue(0);
		this.monthTextField.clear();
		this.yearTextField.clear();
	}

	@FXML
	public void todayOrdersReportButtonOnAction (ActionEvent actionEvent) {
		this.reportGenerator.startGenerateReport("today_orders_report", "order/daily/", "orders-daily", null);
	}

	@FXML
	public void lastWeekOrdersButtonOnAction (ActionEvent actionEvent) {
		this.reportGenerator.startGenerateReport("last_week_orders_report", "order/weekly/", "orders-weekly", null);
	}

	@FXML
	public void monthButtonOnAction (ActionEvent actionEvent) {
		if (this.monthSlider.getValue() == 0.0) {
			new Alert(Alert.AlertType.WARNING, "Monthly report can't generate to on 0 month. Please set value to 1 or above.").show();
			return;
		}

		final Map<String, Object> parameters = new HashMap<>();

		parameters.put("MONTHS", (int) this.monthSlider.getValue());

		this.reportGenerator.startGenerateReport("month_orders_report", "order/month/", "orders-month", parameters);
	}

	@FXML
	public void yearButtonOnAction (ActionEvent actionEvent) {
		if (this.yearSlider.getValue() == 0.0) {
			new Alert(Alert.AlertType.WARNING, "Year report can't generate to on 0 month. Please set value to 1 or above.").show();
			return;
		}

		final Map<String, Object> parameters = new HashMap<>();

		parameters.put("YEARS", (int) this.yearSlider.getValue());

		this.reportGenerator.startGenerateReport("year_orders_report", "order/year/", "orders-year", parameters);
	}

	@FXML
	public void allTimeOrdersButtonOnAction (ActionEvent actionEvent) {
		this.reportGenerator.startGenerateReport("all_orders_report", "order/", "orders", null);
	}
}
