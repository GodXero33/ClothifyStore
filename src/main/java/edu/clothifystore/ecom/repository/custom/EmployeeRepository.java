package edu.clothifystore.ecom.repository.custom;

import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity> {
	EmployeeEntity get (String userName);
	Integer getAdminID (String adminUserName);
}
