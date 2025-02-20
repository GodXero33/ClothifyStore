package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Customer;
import edu.clothifystore.ecom.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {
	int add (Customer customer);
	boolean isPhoneAvailable (String phone);
	boolean isEmailAvailable (String email);
	boolean update (Customer customer);
	boolean delete (Integer id);
	Customer getByPhone (String phone);
	Customer getByEmail (String email);
	List<Customer> getAll ();
}
