package edu.clothifystore.ecom.repository.custom;

import edu.clothifystore.ecom.entity.ProductEntity;
import edu.clothifystore.ecom.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity> {
	int addAndGetID (ProductEntity entity);
	int getCount ();
}
