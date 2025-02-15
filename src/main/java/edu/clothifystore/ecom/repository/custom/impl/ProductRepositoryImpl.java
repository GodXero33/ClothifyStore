package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.ProductEntity;
import edu.clothifystore.ecom.repository.custom.ProductRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
	@Override
	public boolean add (ProductEntity entity) {
		return  false;
	}

	@Override
	public boolean update (ProductEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public ProductEntity get (Integer id) {
		return null;
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
}
