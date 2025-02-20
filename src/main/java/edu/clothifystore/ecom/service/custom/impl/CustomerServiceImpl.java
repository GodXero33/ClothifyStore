package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.entity.CustomerEntity;
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

	@Override
	public boolean isPhoneAvailable (String phone) {
		return this.customerRepository.isPhoneAvailable(phone);
	}

	@Override
	public boolean isEmailAvailable (String email) {
		return this.customerRepository.isEmailAvailable(email);
	}

	@Override
	public boolean update (Customer customer) {
		return this.customerRepository.update(this.mapper.toEntity(customer));
	}

	@Override
	public boolean delete (Integer id) {
		return this.customerRepository.delete(id);
	}

	@Override
	public Customer getByPhone (String phone) {
		final CustomerEntity customerEntity = this.customerRepository.getByPhone(phone);

		if (customerEntity == null) return null;

		return this.mapper.toDTO(customerEntity);
	}

	@Override
	public Customer getByEmail (String email) {
		final CustomerEntity customerEntity = this.customerRepository.getByEmail(email);

		if (customerEntity == null) return null;

		return this.mapper.toDTO(customerEntity);
	}
}
