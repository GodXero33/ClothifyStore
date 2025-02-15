package edu.clothifystore.ecom.util;

import java.sql.*;

public class CrudUtil {
	private CrudUtil () {}

	@SuppressWarnings("unchecked")
	public static <T>T execute (String sql, Object... dataList) throws SQLException {
		final PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
		final int dataLength = dataList.length;

		for (int a = 0; a < dataLength; a++) {
			final Object data = dataList[a];

			if (data == null) {
				preparedStatement.setNull(a + 1, Types.NULL);
			} else {
				preparedStatement.setObject(a + 1, data);
			}
		}

		if (sql.matches("(?i)^select.*")) return (T) preparedStatement.executeQuery();

		return (T)((Integer) preparedStatement.executeUpdate());
	}

	public static int executeWithGeneratedKeys (String sql, Object... dataList) throws SQLException {
		final PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		final int dataLength = dataList.length;

		for (int a = 0; a < dataLength; a++) {
			final Object data = dataList[a];

			if (data == null) {
				preparedStatement.setNull(a + 1, Types.NULL);
			} else {
				preparedStatement.setObject(a + 1, data);
			}
		}

		final int affectedRows = preparedStatement.executeUpdate();

		if (affectedRows == 0) throw new SQLException("No rows affected.");

		final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

		if (!generatedKeys.next()) throw new SQLException("No ID obtained.");

		return generatedKeys.getInt(1);
	}
}
