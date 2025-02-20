package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.service.SuperService;

public interface CustomerService extends SuperService {
	int add (Customer customer);
}
