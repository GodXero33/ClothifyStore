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
import edu.clothifystore.ecom.util.UtilFactory;

public class EmployeeServiceImpl implements EmployeeService {
	private final Mapper<Employee, EmployeeEntity> mapper;
	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl () {
		this.mapper = UtilFactory.getObject(MapperFactory.class).getMapperType(MapperType.EMPLOYEE);
		this.employeeRepository = UtilFactory.getObject(RepositoryFactory.class).getRepositoryType(RepositoryType.EMPLOYEE);
	}

	@Override
	public Employee get (String userName) {
		final EmployeeEntity employee = this.employeeRepository.get(userName);

		if (employee == null) return null;

		return this.mapper.toDTO(employee);
	}

	@Override
	public Employee getByNIC (String nic) {
		final EmployeeEntity employee = this.employeeRepository.getByNIC(nic);

		if (employee == null) return null;

		return this.mapper.toDTO(employee);
	}

	@Override
	public Integer getAdminID (String adminUserName) {
		return this.employeeRepository.getAdminID(adminUserName);
	}

	@Override
	public String getAdminName (Integer adminID) {
		return this.employeeRepository.getAdminName(adminID);
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

	@Override
	public boolean update (Employee employee) {
		return this.employeeRepository.update(this.mapper.toEntity(employee));
	}

	@Override
	public boolean delete (Integer employeeID) {
		return this.employeeRepository.delete(employeeID);
	}

	@Override
	public int getCount () {
		return this.employeeRepository.getCount();
	}
}
