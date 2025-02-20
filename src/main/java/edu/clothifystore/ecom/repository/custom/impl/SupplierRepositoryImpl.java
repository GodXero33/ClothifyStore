package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.SupplierEntity;
import edu.clothifystore.ecom.repository.custom.SupplierRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {
	@Override
	public boolean add (SupplierEntity entity) {
		try {
			CrudUtil.execute(
				"INSERT INTO supplier (name, phone, email, address, type, description) VALUES (?, ?, ?, ?, ?, ?)",
				entity.getName(),
				entity.getPhone(),
				entity.getEmail(),
				entity.getAddress(),
				entity.getType(),
				entity.getDescription()
			);

			return true;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
	}

	@Override
	public boolean update (SupplierEntity entity) {
		try {
			CrudUtil.execute(
				"UPDATE supplier SET name = ?, phone = ?, email = ?, address = ?, type = ?, description = ? WHERE is_deleted = FALSE AND id = ?",
				entity.getName(),
				entity.getPhone(),
				entity.getEmail(),
				entity.getAddress(),
				entity.getType(),
				entity.getDescription(),
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
			CrudUtil.execute("UPDATE supplier SET is_deleted = TRUE WHERE id = ?", id);

			return true;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
	}

	@Override
	public List<SupplierEntity> getAll () {
			final List<SupplierEntity> supplierEntities = new ArrayList<>();

		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id, name, phone, email, address, type, description FROM supplier WHERE is_deleted = FALSE");

			while (resultSet.next()) supplierEntities.add(SupplierEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				email(resultSet.getString(4)).
				address(resultSet.getString(5)).
				type(resultSet.getString(6)).
				description(resultSet.getString(7)).
				build());
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return supplierEntities;
	}

	@Override
	public int getCount () {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM supplier");

			if (resultSet.next()) return resultSet.getInt(1);

			return -1;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return -1;
		}
	}

	private SupplierEntity getSupplierByFieldName (String fieldName, Object searchValue) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id, name, phone, email, address, type, description FROM supplier WHERE is_deleted = FALSE AND " + fieldName + " = ?", searchValue);

			if (resultSet.next()) return SupplierEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				phone(resultSet.getString(3)).
				email(resultSet.getString(4)).
				address(resultSet.getString(5)).
				type(resultSet.getString(6)).
				description(resultSet.getString(7)).
				build();

			return null;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}

	@Override
	public SupplierEntity get (Integer id) {
		return this.getSupplierByFieldName("id", id);
	}

	@Override
	public SupplierEntity getByName (String name) {
		return this.getSupplierByFieldName("name", name);
	}

	@Override
	public SupplierEntity getByPhone (String phone) {
		return this.getSupplierByFieldName("phone", phone);
	}
}
