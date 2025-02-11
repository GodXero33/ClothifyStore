package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.dto.Employee;
import edu.clothifystore.ecom.entity.EmployeeEntity;
import edu.clothifystore.ecom.util.UtilFactory;
import org.modelmapper.ModelMapper;

public class EmployeeMapper implements Mapper<Employee, EmployeeEntity> {
	private static EmployeeMapper instance;

	private EmployeeMapper () {}

	public static EmployeeMapper getInstance () {
		if (EmployeeMapper.instance == null) EmployeeMapper.instance = new EmployeeMapper();

		return EmployeeMapper.instance;
	}

	@Override
	public Employee toDTO (EmployeeEntity entity) {
		final ModelMapper mapper = UtilFactory.getInstance().getObject(ModelMapper.class);
		return mapper.map(entity, Employee.class);
	}

	@Override
	public EmployeeEntity toEntity (Employee dto) {
		final ModelMapper mapper = UtilFactory.getInstance().getObject(ModelMapper.class);
		return mapper.map(dto, EmployeeEntity.class);
	}
}
