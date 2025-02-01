package edu.clothifystore.ecom.util;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
	private static DBConnection instance;

	final private Connection connection;

	private DBConnection () throws SQLException {
		final String HOST = "localhost";
		final Integer PORT = 3306;
		final String DATABASE = "clothify_store";
		final String USER = "root";
		final String PASSWORD = "1234";

		this.connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE), USER, PASSWORD);
	}

	public static DBConnection getInstance () throws SQLException {
		if (DBConnection.instance == null) DBConnection.instance = new DBConnection();
		return DBConnection.instance;
	}
}
