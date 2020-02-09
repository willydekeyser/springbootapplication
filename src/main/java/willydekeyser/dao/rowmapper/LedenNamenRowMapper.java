package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Leden;

public class LedenNamenRowMapper implements RowMapper<Leden>{

	@Override
	public Leden mapRow(ResultSet rs, int rownum) throws SQLException {
		
		Leden leden = new Leden();
		leden.setLeden_id(rs.getInt("leden_id"));
		leden.setVoornaam(rs.getString("voornaam"));
		leden.setFamilienaam(rs.getString("familienaam"));
		leden.setEmailadres(rs.getString("emailadres"));
		return leden;
	}

}
