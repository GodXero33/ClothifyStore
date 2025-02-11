package edu.clothifystore.ecom.repository;

import edu.clothifystore.ecom.repository.custom.impl.EmployeeRepositoryImpl;
import edu.clothifystore.ecom.util.RepositoryType;

public class RepositoryFactory {
	private static RepositoryFactory instance;

	private RepositoryFactory () {}

	public static RepositoryFactory getInstance () {
		if (RepositoryFactory.instance == null) RepositoryFactory.instance = new RepositoryFactory();

		return RepositoryFactory.instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends SuperRepository> T getRepositoryType (RepositoryType repositoryType) {
		return switch (repositoryType) {
			case CUSTOMER -> null;
			case EMPLOYEE -> (T) EmployeeRepositoryImpl.getInstance();
			case ORDER -> null;
			case ORDER_ITEM -> null;
			case PRODUCT -> null;
			case PRODUCT_SUPPLIER -> null;
			case SUPPLIER -> null;
			case REPORT -> null;
		};
	}
}
