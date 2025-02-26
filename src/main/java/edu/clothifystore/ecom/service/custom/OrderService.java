package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Order;
import edu.clothifystore.ecom.service.SuperService;

public interface OrderService extends SuperService {
	int getCount ();
	int add (Order order);
}
