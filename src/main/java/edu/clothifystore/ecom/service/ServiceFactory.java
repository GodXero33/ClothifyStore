package edu.clothifystore.ecom.service;

import edu.clothifystore.ecom.service.custom.impl.*;
import edu.clothifystore.ecom.util.ServiceType;
import edu.clothifystore.ecom.util.UtilFactory;

public class ServiceFactory {
	@SuppressWarnings("unchecked")
	public <T extends SuperService> T getServiceType (ServiceType serviceType) {
		return switch (serviceType) {
			case CUSTOMER -> (T) UtilFactory.getObject(CustomerServiceImpl.class);
			case EMPLOYEE -> (T) UtilFactory.getObject(EmployeeServiceImpl.class);
			case ORDER -> (T) UtilFactory.getObject(OrderServiceImpl.class);
			case PRODUCT -> (T) UtilFactory.getObject(ProductServiceImpl.class);
			case SUPPLIER -> (T) UtilFactory.getObject(SupplierServiceImpl.class);
			case ORDER_ITEM, PRODUCT_SUPPLIER -> null;
		};
	}
}
