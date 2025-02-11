package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.OrderEntity;
import edu.clothifystore.ecom.repository.custom.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
	@Override
	public boolean add (OrderEntity entity) {
		return false;
	}

	@Override
	public boolean update (OrderEntity entity) {
		return false;
	}

	@Override
	public boolean delete (Integer id) {
		return false;
	}

	@Override
	public OrderEntity get (Integer id) {
		return null;
	}

	@Override
	public List<OrderEntity> getAll () {
		return List.of();
	}
}
