package willydekeyser.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import willydekeyser.dao.IUserDAO;
import willydekeyser.dao.resultsetextractor.UserExtractor;
import willydekeyser.dao.rowmapper.RoleRowMapper;
import willydekeyser.model.Role;
import willydekeyser.model.User;

@Transactional
@Repository
public class UserDAO implements IUserDAO {

	private final String sql_findRolesByUserName = "SELECT username, role FROM user_roles WHERE username=?"; 
	private final String sql_findUserByUserName = "SELECT username, password, gegevenspaswoord, enabled, email, accountnonexpired, accountnonlocked, credentialsnonexpired FROM users WHERE username=?";
	
	@Autowired
	@Qualifier("jdbcSecond")
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public User findByUserName(String username) {
		User user = jdbcTemplate.query(sql_findUserByUserName, new UserExtractor(), username);
		List<Role> roles = jdbcTemplate.query(sql_findRolesByUserName, new RoleRowMapper(), username);
		user.setRoles(roles);
		return user;
	}

}
