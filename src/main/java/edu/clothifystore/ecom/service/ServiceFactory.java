package edu.clothifystore.ecom.service;

import edu.clothifystore.ecom.service.custom.impl.EmployeeServiceImpl;
import edu.clothifystore.ecom.util.ServiceType;

public class ServiceFactory {
	private static ServiceFactory instance;

	private ServiceFactory () {}

	public static ServiceFactory getInstance () {
		if (ServiceFactory.instance == null) ServiceFactory.instance = new ServiceFactory();

		return ServiceFactory.instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends SuperService> T getServiceType (ServiceType serviceType) {
		return switch (serviceType) {
			case USER -> (T) EmployeeServiceImpl.getInstance();
		};
	}
}
