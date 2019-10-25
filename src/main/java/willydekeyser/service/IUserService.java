package willydekeyser.service;

import willydekeyser.model.User;

public interface IUserService {

	User findByUserName(String username);
}
