package edu.clothifystore.ecom.repository.custom;

import edu.clothifystore.ecom.entity.OrderEntity;
import edu.clothifystore.ecom.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity> {
	int getCount ();
	int addAndGetId (OrderEntity entity);
}
