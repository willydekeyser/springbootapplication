package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Kasboek;

public class KasboekRowMapper implements RowMapper<Kasboek>{

	@Override
	public Kasboek mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Kasboek kasboek = new Kasboek();
		kasboek.setId(rs.getInt("kasboek_id"));
		kasboek.setJaartal(rs.getInt("jaartal"));
		kasboek.setRubriekId(rs.getInt("rubriekid"));
		kasboek.setOmschrijving(rs.getString("omschrijving"));
		kasboek.setDatum(rs.getDate("datum").toLocalDate());
		kasboek.setUitgaven(rs.getBigDecimal("uitgaven"));
		kasboek.setInkomsten(rs.getBigDecimal("inkomsten"));
		return kasboek;
	}

}
