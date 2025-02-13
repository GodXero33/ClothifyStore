package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.entity.CustomerEntity;

public class CustomerMapper implements Mapper<Customer, CustomerEntity> {
	@Override
	public Customer toDTO (CustomerEntity entity) {
		return null;
	}

	@Override
	public CustomerEntity toEntity (Customer dto) {
		return null;
	}
}
