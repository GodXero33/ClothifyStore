package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.entity.CustomerEntity;
import edu.clothifystore.ecom.util.UtilFactory;
import org.modelmapper.ModelMapper;

public class CustomerMapper implements Mapper<Customer, CustomerEntity> {
	@Override
	public Customer toDTO (CustomerEntity entity) {
		return UtilFactory.getObject(ModelMapper.class).map(entity, Customer.class);
	}

	@Override
	public CustomerEntity toEntity (Customer dto) {
		return UtilFactory.getObject(ModelMapper.class).map(dto, CustomerEntity.class);
	}
}
