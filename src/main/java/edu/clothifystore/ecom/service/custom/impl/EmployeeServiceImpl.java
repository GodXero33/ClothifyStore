package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.mapper.Mapper;
import edu.clothifystore.ecom.mapper.MapperFactory;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.EmployeeRepository;
import edu.clothifystore.ecom.service.custom.EmployeeService;
import edu.clothifystore.ecom.util.MapperType;
import edu.clothifystore.ecom.util.RepositoryType;

public class EmployeeServiceImpl implements EmployeeService {
	private static EmployeeServiceImpl instance;

	private final Mapper<Employee, EmployeeEntity> mapper;

	private final EmployeeRepository employeeRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.EMPLOYEE);

	private EmployeeServiceImpl () {
		this.mapper = MapperFactory.getInstance().getMapperType(MapperType.EMPLOYEE);
	}

	public static EmployeeServiceImpl getInstance () {
		if (EmployeeServiceImpl.instance == null) EmployeeServiceImpl.instance = new EmployeeServiceImpl();

		return EmployeeServiceImpl.instance;
	}

	@Override
	public Employee get (String userName) {
		final EmployeeEntity user = this.employeeRepository.get(userName);

		if (user == null) return null;

		return this.mapper.toDTO(user);
	}

	@Override
	public Integer getAdminID (String adminUserName) {
		return this.employeeRepository.getAdminID(adminUserName);
	}

	@Override
	public boolean add (Employee employee) {
		return this.employeeRepository.add(this.mapper.toEntity(employee));
	}

	@Override
	public boolean isUsernameAvailable (String username) {
		return this.employeeRepository.isUsernameAvailable(username);
	}

	@Override
	public boolean isNICAvailable (String nic) {
		return this.employeeRepository.isNICAvailable(nic);
	}

	@Override
	public boolean isPhoneAvailable (String phone) {
		return this.employeeRepository.isPhoneAvailable(phone);
	}
}
