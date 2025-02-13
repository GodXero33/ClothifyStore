package edu.clothifystore.ecom.mapper;

import edu.clothifystore.ecom.util.MapperType;
import edu.clothifystore.ecom.util.UtilFactory;

public class MapperFactory {
	@SuppressWarnings("unchecked")
	public <D, E> Mapper<D, E> getMapperType (MapperType mapperType) {
		return switch (mapperType) {
			case CUSTOMER -> (Mapper<D, E>) UtilFactory.getObject(CustomerMapper.class);
			case EMPLOYEE -> (Mapper<D, E>) UtilFactory.getObject(EmployeeMapper.class);
			case ORDER -> (Mapper<D, E>) UtilFactory.getObject(OrderMapper.class);
			case PRODUCT -> (Mapper<D, E>) UtilFactory.getObject(ProductMapper.class);
			case SUPPLIER -> (Mapper<D, E>) UtilFactory.getObject(SupplierMapper.class);
			case ORDER_ITEM, PRODUCT_SUPPLIER -> null;
		};
	}
}
