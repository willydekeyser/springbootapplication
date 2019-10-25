package willydekeyser.dao;

import willydekeyser.model.User;

public interface IUserDAO {

	User findByUserName(String username);
}
