package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.ProductEntity;
import edu.clothifystore.ecom.repository.custom.ProductRepository;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
	@Override
	public boolean add (ProductEntity entity) {
		return false;
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
}
