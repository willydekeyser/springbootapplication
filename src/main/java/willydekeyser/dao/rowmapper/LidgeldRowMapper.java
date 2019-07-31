package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Lidgeld;

public class LidgeldRowMapper implements RowMapper<Lidgeld>{

	@Override
	public Lidgeld mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Lidgeld lidgeld = new Lidgeld();
		lidgeld.setId(rs.getInt("lidgeld_id"));
		lidgeld.setLedenId(rs.getInt("ledenlijstid"));
		lidgeld.setDatum(rs.getDate("datum").toLocalDate());
		lidgeld.setBedrag(rs.getBigDecimal("bedrag"));
		return lidgeld;
	}

}
