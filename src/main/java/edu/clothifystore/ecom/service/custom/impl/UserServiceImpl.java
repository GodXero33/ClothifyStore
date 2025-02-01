package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.entity.UserEntity;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.UserRepository;
import edu.clothifystore.ecom.service.custom.UserService;
import edu.clothifystore.ecom.util.RepositoryType;

public class UserServiceImpl implements UserService {
	private static UserServiceImpl instance;

	private final UserRepository userRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.USER);

	private UserServiceImpl () {}

	public static UserServiceImpl getInstance () {
		if (UserServiceImpl.instance == null) UserServiceImpl.instance = new UserServiceImpl();

		return UserServiceImpl.instance;
	}

	@Override
	public User get (String userName) {
		final UserEntity user = this.userRepository.get(userName);

		if (user == null) return null;

		return User.builder().
			id(user.getId()).
			userName(user.getUserName()).
			initials(user.getInitials()).
			firstName(user.getFirstName()).
			lastName(user.getLastName()).
			NIC(user.getNIC()).
			email(user.getEmail()).
			address(user.getAddress()).
			DOB(user.getDOB()).
			password(user.getPassword()).
			salary(user.getSalary()).
			type(user.getType()).
			role(user.getRole()).
			adminID(user.getAdminID()).
			build();
	}
}
