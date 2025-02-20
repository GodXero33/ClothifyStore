package edu.clothifystore.ecom.repository.custom;

import edu.clothifystore.ecom.entity.CustomerEntity;
import edu.clothifystore.ecom.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity> {
	int addAndGetID (CustomerEntity entity);
	boolean isPhoneAvailable (String phone);
	boolean isEmailAvailable (String email);
	CustomerEntity getByPhone (String phone);
	CustomerEntity getByEmail (String email);
}
