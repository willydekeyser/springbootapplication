package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Kasboek;
import willydekeyser.model.Rubriek;

public class KasboekRubriekRowMapper implements RowMapper<Kasboek>{

	@Override
	public Kasboek mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		//ResultSetMetaData rsmd = rs.getMetaData();
		//for (int i=1; i<=rsmd.getColumnCount(); i++) {
		//System.out.println("["+rsmd.getColumnName(i)+"]");
		//}
		
		Kasboek kasboek = new Kasboek();
		kasboek.setId(rs.getInt("kasboek_id"));
		kasboek.setJaartal(rs.getInt("jaartal"));
		kasboek.setRubriekId(rs.getInt("rubriekid"));
		kasboek.setRubriek(new Rubriek(rs.getInt("rubriek_id"), rs.getString("rubriek")));
		kasboek.setOmschrijving(rs.getString("omschrijving"));
		kasboek.setDatum(rs.getDate("datum").toLocalDate());
		kasboek.setUitgaven(rs.getBigDecimal("uitgaven"));
		kasboek.setInkomsten(rs.getBigDecimal("inkomsten"));
		return kasboek;
	}

}