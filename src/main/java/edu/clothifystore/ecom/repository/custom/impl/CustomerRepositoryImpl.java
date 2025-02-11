package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.CustomerEntity;
import edu.clothifystore.ecom.repository.custom.CustomerRepository;

import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
	@Override
	public boolean add (CustomerEntity entity) {
		return false;
	}

	@Override
	public boolean update (CustomerEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public CustomerEntity get (Integer id) {
		return null;
	}

	@Override
	public List<CustomerEntity> getAll () {
		return List.of();
	}
}
