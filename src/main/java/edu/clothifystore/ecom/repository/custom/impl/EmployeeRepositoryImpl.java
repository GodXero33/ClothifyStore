package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.entity.EmployeePhoneEntity;
import edu.clothifystore.ecom.repository.custom.EmployeeRepository;
import edu.clothifystore.ecom.util.CrudUtil;
import edu.clothifystore.ecom.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
	private static EmployeeRepositoryImpl instance;

	private EmployeeRepositoryImpl () {}

	public static EmployeeRepository getInstance () {
		if (EmployeeRepositoryImpl.instance == null) EmployeeRepositoryImpl.instance = new EmployeeRepositoryImpl();

		return EmployeeRepositoryImpl.instance;
	}

	private String getInsertQueryBuilder (EmployeeEntity entity) {
		final StringBuilder builder = new StringBuilder();
		int optionalParams = 0;

		builder.append("INSERT INTO employee (user_name, full_name, nic, address, dob, type, role");

		if (entity.getEmail() != null) {
			builder.append(", email");
			optionalParams++;
		}

		if (entity.getSalary() != null) {
			builder.append(", salary");
			optionalParams++;
		}

		if (entity.getAdminID() != null) {
			builder.append(", admin_id");
			optionalParams++;
		}

		builder.append(") VALUES (");

		final int paramsLength = optionalParams + 7;

		for (int a = 0; a < paramsLength; a++) builder.append(a == paramsLength - 1 ? "?" : "?, ");

		builder.append(")");

		return builder.toString();
	}

	@Override
	public boolean add (EmployeeEntity entity) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			connection.setAutoCommit(false); // Start transaction.


			final PreparedStatement employeeInsertStatement = connection.prepareStatement(this.getInsertQueryBuilder(entity), Statement.RETURN_GENERATED_KEYS); // Insert new record into employee table and get auto generated id value.

			employeeInsertStatement.setString(1, entity.getUserName());
			employeeInsertStatement.setString(2, entity.getFullName());
			employeeInsertStatement.setString(3, entity.getNIC());
			employeeInsertStatement.setString(4, entity.getAddress());
			employeeInsertStatement.setString(5, entity.getDOB());
			employeeInsertStatement.setString(6, entity.getType());
			employeeInsertStatement.setString(7, entity.getRole());

			int optionalParams = 8; // There is 7 required values are already have bind. Now start with 8.

			if (entity.getEmail() != null) employeeInsertStatement.setString(optionalParams++, entity.getEmail()); // 8
			if (entity.getSalary() != null) employeeInsertStatement.setDouble(optionalParams++, entity.getSalary()); // 9
			if (entity.getAdminID() != null) employeeInsertStatement.setInt(optionalParams, entity.getAdminID()); // 10

			final int affectedRows = employeeInsertStatement.executeUpdate();

			if (affectedRows == 0) throw new SQLException("Insert new employee failed. no rows affected.");

			final ResultSet generatedKeys = employeeInsertStatement.getGeneratedKeys();

			if (!generatedKeys.next()) throw new SQLException("Creating order failed, no ID obtained.");

			final List<EmployeePhoneEntity> phoneEntities = entity.getPhone();

			if (phoneEntities.isEmpty()) return true;

			final PreparedStatement employeePhoneInsertStatement = connection.prepareStatement("INSERT INTO employee_phone (phone, employee_id, type) VALUES (?, ?, ?)"); // Insert new records into employee_phone table.

			employeePhoneInsertStatement.setInt(2, generatedKeys.getInt(1));

			// For each phone in employee entity phone field, insert into employee_phone table as records.
			for (final EmployeePhoneEntity phoneEntity : phoneEntities) {
				employeePhoneInsertStatement.setString(1, phoneEntity.getPhone());
				employeePhoneInsertStatement.setString(3, phoneEntity.getType());
				employeePhoneInsertStatement.executeUpdate();
			}

			return true;
		} catch (SQLException exception) { // Any failure happens while executing or inserting records, rollback(delete buffer) changes.
			try {
				DBConnection.getInstance().getConnection().rollback();
			} catch (SQLException rollbackException) {
				System.out.println(rollbackException.getMessage());
			}

			System.out.println(exception.getMessage());
		} finally { // Either transaction success or fail, turn on auto commit to stop transaction.
			try {
				DBConnection.getInstance().getConnection().setAutoCommit(true);
			} catch (SQLException exception) {
				System.out.println(exception.getMessage());
			}
		}

		return false;
	}

	@Override
	public boolean update (EmployeeEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public EmployeeEntity get (Integer id) {
		return null;
	}

	@Override
	public EmployeeEntity get (String username) {
		try {
			final ResultSet employeeDataResultSet = CrudUtil.execute("SELECT id, user_name, full_name, nic, email, address, dob, password, salary, type, role, admin_id FROM employee WHERE user_name = ?", username);

			if (!employeeDataResultSet.next()) return null;

			final int employeeID = employeeDataResultSet.getInt(1);
			final EmployeeEntity employeeEntity = EmployeeEntity.builder().
				id(employeeID).
				userName(employeeDataResultSet.getString(2)).
				fullName(employeeDataResultSet.getString(3)).
				NIC(employeeDataResultSet.getString(4)).
				email(employeeDataResultSet.getString(5)).
				address(employeeDataResultSet.getString(6)).
				DOB(employeeDataResultSet.getString(7)).
				password(employeeDataResultSet.getString(8)).
				salary(employeeDataResultSet.getDouble(9)).
				type(employeeDataResultSet.getString(10)).
				role(employeeDataResultSet.getString(11)).
				adminID(employeeDataResultSet.getInt(12)).
				build();
			final List<EmployeePhoneEntity> employeePhone = new ArrayList<>();

			final ResultSet emloyeePhoneResultSet = CrudUtil.execute("SELECT phone, type FROM employee_phone WHERE employee_id = ?", employeeID);

			// Add each phone records into 'employeePhone' list.
			while (emloyeePhoneResultSet.next())
				employeePhone.add(EmployeePhoneEntity.builder().
					phone(emloyeePhoneResultSet.getString(1)).
					employeeID(employeeID).
					type(emloyeePhoneResultSet.getString(2)).
					build());

			employeeEntity.setPhone(employeePhone);
			return employeeEntity;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return null;
	}

	@Override
	public Integer getAdminID (String adminUserName) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id FROM employee WHERE user_name = ? AND type = 'ADMIN'", adminUserName);

			if (resultSet.next()) return resultSet.getInt(1);
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return -1;
	}

	private boolean isPrimaryFieldValueAvailable (String query, Object data) {
		try {
			return ((ResultSet) CrudUtil.execute(query, data)).next();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return false;
	}

	@Override
	public boolean isUsernameAvailable (String username) {
		return this.isPrimaryFieldValueAvailable("SELECT id FROM employee WHERE user_name = ?", username);
	}

	@Override
	public boolean isNICAvailable (String nic) {
		return this.isPrimaryFieldValueAvailable("SELECT id FROM employee WHERE nic = ?", nic);
	}

	@Override
	public boolean isPhoneAvailable (String phone) {
		return this.isPrimaryFieldValueAvailable("SELECT phone FROM employee_phone WHERE phone = ?", phone);
	}

	@Override
	public List<EmployeeEntity> getAll () {
		return List.of();
	}
}
