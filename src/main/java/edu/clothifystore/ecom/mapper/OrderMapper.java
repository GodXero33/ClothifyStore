package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Order;
import edu.clothifystore.ecom.entity.OrderEntity;

public class OrderMapper implements Mapper<Order, OrderEntity> {
	@Override
	public Order toDTO (OrderEntity entity) {
		return null;
	}

	@Override
	public OrderEntity toEntity (Order dto) {
		return null;
	}
}
