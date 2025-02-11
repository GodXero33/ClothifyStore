package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.SupplierEntity;
import edu.clothifystore.ecom.repository.custom.SupplierRepository;

import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {
	@Override
	public boolean add (SupplierEntity entity) {
		return false;
	}

	@Override
	public boolean update (SupplierEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public SupplierEntity get (Integer id) {
		return null;
	}

	@Override
	public List<SupplierEntity> getAll () {
		return List.of();
	}
}
