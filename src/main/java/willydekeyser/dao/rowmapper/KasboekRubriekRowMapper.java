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
		kasboek.setId(rs.getInt("Id"));
		kasboek.setJaartal(rs.getInt("Jaartal"));
		kasboek.setRubriekId(rs.getInt("RubriekId"));
		kasboek.setRubriek(new Rubriek(rs.getInt("id"), rs.getString("rubriek")));
		kasboek.setOmschrijving(rs.getString("Omschrijving"));
		kasboek.setDatum(rs.getDate("Datum").toLocalDate());
		kasboek.setUitgaven(rs.getBigDecimal("Uitgaven"));
		kasboek.setInkomsten(rs.getBigDecimal("Inkomsten"));
		return kasboek;
	}

}