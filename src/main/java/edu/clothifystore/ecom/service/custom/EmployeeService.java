package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.SuperService;

public interface EmployeeService extends SuperService {
	Employee get (String userName);
	Integer getAdminID (String adminUserName);
	boolean add (Employee employee);
}
