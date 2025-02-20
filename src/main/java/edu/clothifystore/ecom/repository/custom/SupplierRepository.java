package edu.clothifystore.ecom.repository.custom;

import edu.clothifystore.ecom.entity.SupplierEntity;
import edu.clothifystore.ecom.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<SupplierEntity> {
	int getCount ();
	SupplierEntity getByName (String name);
	SupplierEntity getByPhone (String phone);
}
