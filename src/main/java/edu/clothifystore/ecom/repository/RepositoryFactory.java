package edu.clothifystore.ecom.repository;

import edu.clothifystore.ecom.repository.custom.impl.*;
import edu.clothifystore.ecom.util.RepositoryType;
import edu.clothifystore.ecom.util.UtilFactory;

public class RepositoryFactory {
	@SuppressWarnings("unchecked")
	public <T extends SuperRepository> T getRepositoryType (RepositoryType repositoryType) {
		return switch (repositoryType) {
			case CUSTOMER -> (T) UtilFactory.getObject(CustomerRepositoryImpl.class);
			case EMPLOYEE -> (T) UtilFactory.getObject(EmployeeRepositoryImpl.class);
			case ORDER -> (T) UtilFactory.getObject(OrderRepositoryImpl.class);
			case PRODUCT -> (T) UtilFactory.getObject(ProductRepositoryImpl.class);
			case SUPPLIER -> (T) UtilFactory.getObject(SupplierRepositoryImpl.class);
			case ORDER_ITEM, PRODUCT_SUPPLIER -> null;
		};
	}
}
