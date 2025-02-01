package edu.clothifystore.ecom.service.custom;

import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.service.SuperService;

public interface UserService extends SuperService {
	User get (String userName);
}
