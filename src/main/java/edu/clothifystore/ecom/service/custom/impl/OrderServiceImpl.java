package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Order;
import edu.clothifystore.ecom.entity.OrderEntity;
import edu.clothifystore.ecom.mapper.Mapper;
import edu.clothifystore.ecom.mapper.MapperFactory;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.OrderRepository;
import edu.clothifystore.ecom.service.custom.OrderService;
import edu.clothifystore.ecom.util.MapperType;
import edu.clothifystore.ecom.util.RepositoryType;
import edu.clothifystore.ecom.util.UtilFactory;

public class OrderServiceImpl implements OrderService {
	private final Mapper<Order, OrderEntity> mapper;
	private final OrderRepository orderRepository;

	public OrderServiceImpl () {
		this.mapper = UtilFactory.getObject(MapperFactory.class).getMapperType(MapperType.ORDER);
		this.orderRepository = UtilFactory.getObject(RepositoryFactory.class).getRepositoryType(RepositoryType.ORDER);
	}

	@Override
	public int getCount () {
		return this.orderRepository.getCount();
	}

	@Override
	public int add (Order order) {
		return this.orderRepository.addAndGetId(this.mapper.toEntity(order));
	}
}
