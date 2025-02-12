package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.service.SuperService;

public interface EmployeeService extends SuperService {
	Employee get (String userName);
	Employee getByNIC (String nic);
	Integer getAdminID (String adminUserName);
	String getAdminName (Integer adminID);
	boolean add (Employee employee);
	boolean isUsernameAvailable (String username);
	boolean isNICAvailable (String nic);
	boolean isPhoneAvailable (String phone);
	boolean update (Employee employee);
}
