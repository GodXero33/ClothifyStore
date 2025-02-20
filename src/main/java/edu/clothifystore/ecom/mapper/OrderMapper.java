package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Order;
import edu.clothifystore.ecom.entity.OrderEntity;
import edu.clothifystore.ecom.util.UtilFactory;
import org.modelmapper.ModelMapper;

public class OrderMapper implements Mapper<Order, OrderEntity> {
	@Override
	public Order toDTO (OrderEntity entity) {
		return UtilFactory.getObject(ModelMapper.class).map(entity, Order.class);
	}

	@Override
	public OrderEntity toEntity (Order dto) {
		return UtilFactory.getObject(ModelMapper.class).map(dto, OrderEntity.class);
	}
}
