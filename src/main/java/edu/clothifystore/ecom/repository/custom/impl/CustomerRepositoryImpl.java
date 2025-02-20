package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.CustomerEntity;
import edu.clothifystore.ecom.repository.custom.CustomerRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
	@Override
	public boolean add (CustomerEntity entity) {
		return false;
	}

	@Override
	public boolean update (CustomerEntity entity) {
		try {
			CrudUtil.execute(
				"UPDATE customer SET name = ?, phone = ?, email = ?, address = ? WHERE is_deleted = FALSE AND id = ?",
				entity.getName(),
				entity.getPhone(),
				entity.getEmail(),
				entity.getAddress(),
				entity.getId()
			);

			return true;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());

			return false;
		}
	}

	@Override
	public boolean delete (Integer id) {
		try {
			CrudUtil.execute("UPDATE customer SET is_deleted = TRUE WHERE id = ?", id);

			return true;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());

			return false;
		}
	}

	@Override
	public CustomerEntity get (Integer id) {
		return null;
	}

	@Override
	public List<CustomerEntity> getAll () {
		return List.of();
	}

	@Override
	public int addAndGetID (CustomerEntity entity) {
		try {
			return CrudUtil.executeWithGeneratedKeys(
				"INSERT INTO customer (name, phone, email, address) VALUES (?, ?, ?, ?)",
				entity.getName(),
				entity.getPhone(),
				entity.getEmail(),
				entity.getAddress()
			);
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());

			return 0;
		}
	}

	@Override
	public boolean isPhoneAvailable (String phone) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id FROM customer WHERE is_deleted = FALSE AND phone = ?", phone);

			return resultSet.next();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
	}

	@Override
	public boolean isEmailAvailable (String email) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id FROM customer WHERE is_deleted = FALSE AND email = ?", email);

			return resultSet.next();
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
	}

	private CustomerEntity getByFieldName (String fieldName, Object searchValue) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id, name, phone, email, address FROM customer WHERE is_deleted = FALSE AND " + fieldName + " = ?", searchValue);

			if (resultSet.next()) return CustomerEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				email(resultSet.getString(4)).
				address(resultSet.getString(5)).
				build();

			return null;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}

	@Override
	public CustomerEntity getByPhone (String phone) {
		return this.getByFieldName("phone", phone);
	}

	@Override
	public CustomerEntity getByEmail (String email) {
		return this.getByFieldName("email", email);
	}
}
