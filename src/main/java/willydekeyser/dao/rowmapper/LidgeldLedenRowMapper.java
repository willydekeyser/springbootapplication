package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;

public class LidgeldLedenRowMapper implements RowMapper<Lidgeld>{

	@Override
	public Lidgeld mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Lidgeld lidgeld = new Lidgeld();
		lidgeld.setId(rs.getInt("lidgeld_id"));
		lidgeld.setLedenId(rs.getInt("ledenlijstid"));
		lidgeld.setLeden(new Leden(
				rs.getInt("ledenlijst_id"), 
				rs.getString("voornaam"),
				rs.getString("familienaam"),
				rs.getString("straat"),
				rs.getString("nr"),
				rs.getString("postnr"),
				rs.getString("gemeente"),
				rs.getString("telefoonnummer"),
				rs.getString("gsmnummer"),
				rs.getString("emailadres"),
				rs.getString("webadres"),
				rs.getDate("datumlidgeld").toLocalDate(),
				rs.getInt("soortenledenid"),
				rs.getBoolean("ontvangmail"),
				rs.getBoolean("mailvlag")));
		lidgeld.setDatum(rs.getDate("datum").toLocalDate());
		lidgeld.setBedrag(rs.getBigDecimal("bedrag"));
		return lidgeld;
	}

}
