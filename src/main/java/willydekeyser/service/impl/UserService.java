package willydekeyser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.dao.IUserDAO;
import willydekeyser.model.User;
import willydekeyser.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	
	@Override
	public User findByUserName(String username) {
		return userDAO.findByUserName(username);
	}

}
