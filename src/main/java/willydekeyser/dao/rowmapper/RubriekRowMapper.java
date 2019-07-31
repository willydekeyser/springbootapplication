package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Rubriek;

public class RubriekRowMapper implements RowMapper<Rubriek>{

	@Override
	public Rubriek mapRow(ResultSet rs, int rowNum) throws SQLException {

		Rubriek rubriek = new Rubriek();
		rubriek.setId(rs.getInt("rubriek_id"));
		rubriek.setRubriek(rs.getString("rubriek"));
		return rubriek;
		
	}

}
