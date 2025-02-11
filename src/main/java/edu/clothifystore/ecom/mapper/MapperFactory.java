package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.util.MapperType;

public class MapperFactory {
	private static MapperFactory instance;

	private MapperFactory () {}

	public static MapperFactory getInstance () {
		if (MapperFactory.instance == null) MapperFactory.instance = new MapperFactory();

		return MapperFactory.instance;
	}

	@SuppressWarnings("unchecked")
	public <D, E> Mapper<D, E> getMapperType (MapperType mapperType) {
		return switch (mapperType) {
			case CUSTOMER -> null;
			case EMPLOYEE -> (Mapper<D, E>) EmployeeMapper.getInstance();
			case ORDER -> (Mapper<D, E>) null;
			case ORDER_ITEM -> (Mapper<D, E>) null;
			case PRODUCT -> (Mapper<D, E>) null;
			case PRODUCT_SUPPLIER -> (Mapper<D, E>) null;
			case SUPPLIER -> (Mapper<D, E>) null;
			case REPORT -> (Mapper<D, E>) null;
		};
	}
}
