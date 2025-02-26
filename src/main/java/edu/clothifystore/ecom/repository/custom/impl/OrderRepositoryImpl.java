package edu.clothifystore.ecom.repository.custom.impl;

import edu.clothifystore.ecom.entity.OrderEntity;
import edu.clothifystore.ecom.entity.OrderItemEntity;
import edu.clothifystore.ecom.repository.custom.OrderRepository;
import edu.clothifystore.ecom.util.CrudUtil;
import edu.clothifystore.ecom.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
	@Override
	public int addAndGetId (OrderEntity entity) {
		try {
			final Connection connection = DBConnection.getInstance().getConnection();

			connection.setAutoCommit(false);

			final int orderID = CrudUtil.executeWithGeneratedKeys("INSERT INTO `order` (date, time, employee_id) VALUES (?, ?, ?)", entity.getDate(), entity.getTime(), entity.getEmployeeID());

			if (orderID == 0) {
				connection.rollback();
				return 0;
			}

			final List<OrderItemEntity> orderItemEntities = entity.getOrderItems();
			final String orderItemInsertQuery = "INSERT INTO order_item (order_id, product_id, quantity, amount, discount) VALUES (?, ?, ?, ?, ?)";

			for (final OrderItemEntity orderItemEntity : orderItemEntities)
				CrudUtil.execute(
					orderItemInsertQuery,
					orderID,
					orderItemEntity.getProduct().getId(),
					orderItemEntity.getQuantity(),
					orderItemEntity.getQuantity() * orderItemEntity.getProduct().getPrice(),
					orderItemEntity.getDiscount()
				);

			connection.commit();

			return orderID;
		} catch (SQLException exception) {
			try {
				DBConnection.getInstance().getConnection().rollback();
			} catch (SQLException rollbackException) {
				System.out.println(rollbackException.getMessage());
			}

			return 0;
		} finally {
			try {
				DBConnection.getInstance().getConnection().setAutoCommit(true);
			} catch (SQLException exception) {
				System.out.println(exception.getMessage());
			}
		}
	}

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
