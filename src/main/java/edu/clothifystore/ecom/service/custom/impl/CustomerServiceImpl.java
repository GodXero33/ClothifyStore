package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.mapper.CustomerMapper;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.CustomerRepository;
import edu.clothifystore.ecom.service.custom.CustomerService;
import edu.clothifystore.ecom.util.RepositoryType;
import edu.clothifystore.ecom.util.UtilFactory;

public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository = UtilFactory.getObject(RepositoryFactory.class).getRepositoryType(RepositoryType.CUSTOMER);
	private final CustomerMapper mapper = UtilFactory.getObject(CustomerMapper.class);

	@Override
	public int add (Customer customer) {
		return this.customerRepository.addAndGetID(this.mapper.toEntity(customer));
	}
}
