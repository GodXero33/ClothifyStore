package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Supplier;
import edu.clothifystore.ecom.service.SuperService;

import java.util.List;

public interface SupplierService extends SuperService {
	int getCount ();
	boolean add (Supplier supplier);
	boolean update (Supplier supplier);
	boolean delete (Integer id);
	Supplier getByName (String name);
	Supplier getByPhone (String phone);
	List<Supplier> getAll ();
}
