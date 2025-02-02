package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.UserEntity;
import edu.clothifystore.ecom.repository.custom.UserRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
	private static UserRepositoryImpl instance;

	private UserRepositoryImpl () {}

	public static UserRepository getInstance () {
		if (UserRepositoryImpl.instance == null) UserRepositoryImpl.instance = new UserRepositoryImpl();

		return UserRepositoryImpl.instance;
	}

	@Override
	public boolean add (UserEntity entity) {
		try {
			return (Integer) (CrudUtil.execute(
				"INSERT INTO `user` (user_name, initials, first_name, last_name, nic, email, address, dob, password, salary, type, role, admin_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				entity.getUserName(),
				entity.getInitials(),
				entity.getFirstName(),
				entity.getLastName(),
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
	public boolean update (UserEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public UserEntity get (Integer id) {
		return null;
	}

	@Override
	public UserEntity get (String username) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id, user_name, initials, first_name, last_name, nic, email, address, dob, password, salary, type, role, admin_id FROM `user` WHERE user_name = ?", username);

			if (resultSet.next()) return UserEntity.builder().
				id(resultSet.getInt(1)).
				userName(resultSet.getString(2)).
				initials(resultSet.getString(3)).
				firstName(resultSet.getString(4)).
				lastName(resultSet.getString(5)).
				NIC(resultSet.getString(6)).
				email(resultSet.getString(7)).
				address(resultSet.getString(8)).
				DOB(resultSet.getString(9)).
				password(resultSet.getString(10)).
				salary(resultSet.getDouble(11)).
				type(resultSet.getString(12)).
				role(resultSet.getString(13)).
				adminID(resultSet.getInt(14)).
				build();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return null;
	}

	@Override
	public Integer getAdminID (String adminUserName) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id FROM `user` WHERE user_name = ? AND type = 'ADMIN'", adminUserName);

			if (resultSet.next()) return resultSet.getInt(1);
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return -1;
	}

	@Override
	public List<UserEntity> getAll () {
		return List.of();
	}
}
