package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.ProductEntity;
import edu.clothifystore.ecom.repository.custom.ProductRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
	@Override
	public boolean add (ProductEntity entity) {
		try {
			CrudUtil.execute(
				"INSERT INTO product (name, gender, size, type, brand, price, quantity, discount, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				entity.getName(),
				entity.getGender(),
				entity.getSize(),
				entity.getType(),
				entity.getBrand(),
				entity.getPrice(),
				entity.getQuantity(),
				entity.getDiscount(),
				entity.getDescription()
			);

			return true;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
	}

	@Override
	public boolean update (ProductEntity entity) {
		try {
			CrudUtil.execute(
				"UPDATE product SET name = ?, gender = ?, size = ?, type = ?, brand = ?, price = ?, quantity = ?, discount = ?, description = ? WHERE is_deleted = FALSE AND id = ?",
				entity.getName(),
				entity.getGender(),
				entity.getSize(),
				entity.getType(),
				entity.getBrand(),
				entity.getPrice(),
				entity.getQuantity(),
				entity.getDiscount(),
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
			CrudUtil.execute("UPDATE product SET is_deleted = TRUE WHERE id = ?", id);

			return true;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
	}

	@Override
	public ProductEntity get (Integer id) {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT id, name, gender, size, type, brand, price, discount, quantity, description FROM product WHERE is_deleted = FALSE AND id = ?", id);

			if (resultSet.next()) return ProductEntity.builder().
				id(resultSet.getInt(1)).
				name(resultSet.getString(2)).
				gender(resultSet.getString(3)).
				size(resultSet.getString(4)).
				type(resultSet.getString(5)).
				brand(resultSet.getString(6)).
				price(resultSet.getDouble(7)).
				discount(resultSet.getDouble(8)).
				quantity(resultSet.getInt(9)).
				description(resultSet.getString(10)).
				build();

			return null;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}

	@Override
	public List<ProductEntity> getAll () {
		return List.of();
	}

	@Override
	public int addAndGetID (ProductEntity entity) {
		try {
			final int productID = CrudUtil.executeWithGeneratedKeys(
				"INSERT INTO product (name, gender, size, type, brand, price, quantity, discount, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				entity.getName(),
				entity.getGender(),
				entity.getSize(),
				entity.getType(),
				entity.getBrand(),
				entity.getPrice(),
				entity.getQuantity(),
				entity.getDiscount(),
				entity.getDescription()
			);

			return productID;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return -1;
		}
	}

	@Override
	public int getCount () {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM product");

			if (resultSet.next()) return resultSet.getInt(1);

			return -1;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return -1;
		}
	}
}
