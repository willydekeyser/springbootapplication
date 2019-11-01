package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.User;

public class UserExtractor implements ResultSetExtractor<User>{

	@Override
	public User extractData(ResultSet rs) throws SQLException, DataAccessException {
		User user = new User();
		while (rs.next()) {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setGegevenspaswoord(rs.getString("gegevenspaswoord"));
			user.setEmail(rs.getString("email"));
			user.setEnabled(rs.getBoolean("enabled"));
			user.setAccountnonexpired(rs.getBoolean("accountnonexpired"));
			user.setAccountnonlocked(rs.getBoolean("accountnonlocked"));
			user.setCredentialsnonexpired(rs.getBoolean("credentialsnonexpired"));
		}
		return user;
	}

}
