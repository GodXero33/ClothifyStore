package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.Product;
import edu.clothifystore.ecom.service.SuperService;

public interface ProductService extends SuperService {
	int addAndGetID (Product product);
}
