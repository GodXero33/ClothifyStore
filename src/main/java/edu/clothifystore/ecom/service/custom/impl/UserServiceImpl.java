package edu.clothifystore.ecom.service.custom.impl;

import edu.clothifystore.ecom.dto.User;
import edu.clothifystore.ecom.entity.UserEntity;
import edu.clothifystore.ecom.repository.RepositoryFactory;
import edu.clothifystore.ecom.repository.custom.UserRepository;
import edu.clothifystore.ecom.service.custom.UserService;
import edu.clothifystore.ecom.util.RepositoryType;
import org.modelmapper.ModelMapper;

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

		return new ModelMapper().map(user, User.class);
	}

	@Override
	public Integer getAdminID (String adminUserName) {
		return this.userRepository.getAdminID(adminUserName);
	}

	@Override
	public boolean add (User user) {
		return this.userRepository.add(new ModelMapper().map(user, UserEntity.class));
	}
}
