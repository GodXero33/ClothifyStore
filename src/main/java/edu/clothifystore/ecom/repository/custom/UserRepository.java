package edu.clothifystore.ecom.repository.custom;

import edu.clothifystore.ecom.entity.UserEntity;
import edu.clothifystore.ecom.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	UserEntity get (String userName);
}
