package edu.clothifystore.ecom.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Scanner;

public class DailyAlertGenerator {
	private final Label alertDisplayLabel;
	private final String userName;
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public DailyAlertGenerator (Label alertDisplayLabel, String userName) {
		this.alertDisplayLabel = alertDisplayLabel;
		this.userName = userName;

		this.start();
	}

	private void start () {
		final int interval = 10;

		final Timeline timeline = new Timeline(
			new KeyFrame(Duration.ZERO, e -> {
				String greeting = generateGreeting();
				String news = fetchNews();

				this.alertDisplayLabel.setText(greeting + "\n" + news);
			}),
			new KeyFrame(Duration.seconds(interval))
		);

		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private String generateGreeting () {
		final int hour = LocalDateTime.now().getHour();

		return "Good " + (hour < 12 ? "Morning " : hour < 15 ? "Afternoon " : "Evening ") + this.userName + "!";
	}

	private String fetchNews () {
		try {
			URL url = new URL("http://localhost:3000/latest-news");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) return "Failed to fetch news.";

			try (Scanner scanner = new Scanner(conn.getInputStream())) {
				String jsonResponse = scanner.useDelimiter("\\A").next();
				JsonNode jsonNode = objectMapper.readTree(jsonResponse);

				return jsonNode.has("headline") ? jsonNode.get("headline").asText() : "No news available.";
			}
		} catch (IOException exception) {
			return "Failed to fetch news.";
		}
	}
}
