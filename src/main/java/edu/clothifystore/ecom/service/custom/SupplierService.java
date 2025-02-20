package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.service.SuperService;

public interface SupplierService extends SuperService {
	int getCount ();
	boolean add (Supplier supplier);
}
