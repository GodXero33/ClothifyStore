package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.OrderEntity;
import edu.clothifystore.ecom.repository.custom.OrderRepository;
import edu.clothifystore.ecom.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	@Override
	public int getCount () {
		try {
			final ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM `order`");

			if (resultSet.next()) return resultSet.getInt(1);

			return -1;
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return -1;
		}
	}
}
