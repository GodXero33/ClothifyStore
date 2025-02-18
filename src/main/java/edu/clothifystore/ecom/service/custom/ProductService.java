package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.service.SuperService;

public interface ProductService extends SuperService {
	boolean add (Product product);
	int addAndGetID (Product product);
	boolean update (Product product);
	Product get (int id);
	boolean delete (int id);
	int getCount ();
}
