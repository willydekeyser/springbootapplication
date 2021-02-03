package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Roles;

public class RoleRowMapper implements RowMapper<Roles>{

	@Override
	public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
		Roles role = new Roles();
		role.setUsername(rs.getString("username"));
		role.setRole(rs.getString("role"));
		return role;
	}

}
