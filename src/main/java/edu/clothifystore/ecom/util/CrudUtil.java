package edu.clothifystore.ecom.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
	private CrudUtil () {}

	@SuppressWarnings("unchecked")
	public static <T>T execute (String sql, Object... dataList) throws SQLException {
		final PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
		final int dataLength = dataList.length;

		for (int a = 0; a < dataLength; a++) preparedStatement.setObject(a + 1, dataList[a]);

		if (sql.matches("(?i)^select.*")) return (T) preparedStatement.executeQuery();

		return (T)((Integer) preparedStatement.executeUpdate());
	}
}
