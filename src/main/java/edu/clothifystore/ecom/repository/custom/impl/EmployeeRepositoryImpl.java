package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.repository.custom.EmployeeRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
	private static EmployeeRepositoryImpl instance;

	private EmployeeRepositoryImpl() {}

	public static EmployeeRepository getInstance () {
		if (EmployeeRepositoryImpl.instance == null) EmployeeRepositoryImpl.instance = new EmployeeRepositoryImpl();

		return EmployeeRepositoryImpl.instance;
	}

	@Override
	public boolean add (EmployeeEntity entity) {
		try {
			return (Integer) (CrudUtil.execute(
				"INSERT INTO employee (user_name, full_name, nic, email, address, dob, password, salary, type, role, admin_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				entity.getUserName(),
				entity.getFullName(),
				entity.getNIC(),
				entity.getEmail(),
				entity.getAddress(),
				entity.getDOB(),
				entity.getPassword(),
				entity.getSalary(),
				entity.getType(),
				entity.getRole(),
				entity.getAdminID()
			)) == 1;
		} catch (SQLException exception) {
			System.out.println(exception);
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
			final ResultSet resultSet = CrudUtil.execute("SELECT user_name, full_name, nic, email, address, dob, password, salary, type, role, admin_id FROM employee WHERE user_name = ?", username);

			if (resultSet.next()) return EmployeeEntity.builder().
				userName(resultSet.getString(1)).
				fullName(resultSet.getString(2)).
				NIC(resultSet.getString(3)).
				email(resultSet.getString(4)).
				address(resultSet.getString(5)).
				DOB(resultSet.getString(6)).
				password(resultSet.getString(7)).
				salary(resultSet.getDouble(8)).
				type(resultSet.getString(9)).
				role(resultSet.getString(10)).
				adminID(resultSet.getInt(11)).
				build();
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

	@Override
	public List<EmployeeEntity> getAll () {
		return List.of();
	}
}
