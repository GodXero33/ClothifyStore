package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.util.UtilFactory;
import org.modelmapper.ModelMapper;

public class EmployeeMapper implements Mapper<Employee, EmployeeEntity> {
	@Override
	public Employee toDTO (EmployeeEntity entity) {
		return UtilFactory.getObject(ModelMapper.class).map(entity, Employee.class);
	}

	@Override
	public EmployeeEntity toEntity (Employee dto) {
		return UtilFactory.getObject(ModelMapper.class).map(dto, EmployeeEntity.class);
	}
}
