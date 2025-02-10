package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.EmployeeRepository;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.RepositoryType;
import org.modelmapper.ModelMapper;

public class EmployeeServiceImpl implements EmployeeService {
	private static EmployeeServiceImpl instance;

	private final EmployeeRepository employeeRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.USER);

	private EmployeeServiceImpl() {}

	public static EmployeeServiceImpl getInstance () {
		if (EmployeeServiceImpl.instance == null) EmployeeServiceImpl.instance = new EmployeeServiceImpl();

		return EmployeeServiceImpl.instance;
	}

	@Override
	public Employee get (String userName) {
		final EmployeeEntity user = this.employeeRepository.get(userName);

		if (user == null) return null;

		return new ModelMapper().map(user, Employee.class);
	}

	@Override
	public Integer getAdminID (String adminUserName) {
		return this.employeeRepository.getAdminID(adminUserName);
	}

	@Override
	public boolean add (Employee employee) {
		return this.employeeRepository.add(new ModelMapper().map(employee, EmployeeEntity.class));
	}
}
