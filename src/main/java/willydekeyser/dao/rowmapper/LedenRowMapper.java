package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.Leden;

public class LedenRowMapper implements RowMapper<Leden>{

	@Override
	public Leden mapRow(ResultSet rs, int rownum) throws SQLException {
		
		Leden leden = new Leden();
		leden.setId(rs.getInt("ledenlijst_id"));
		leden.setVoornaam(rs.getString("voornaam"));
		leden.setFamilienaam(rs.getString("familienaam"));
		leden.setStraat(rs.getString("straat"));
		leden.setNr(rs.getString("nr"));
		leden.setPostnr(rs.getString("postnr"));
		leden.setGemeente(rs.getString("gemeente"));
		leden.setTelefoonnummer(rs.getString("telefoonnummer"));
		leden.setGsmnummer(rs.getString("gsmnummer"));
		leden.setEmailadres(rs.getString("emailadres"));
		leden.setWebadres(rs.getString("webadres"));
		leden.setDatumlidgeld(rs.getDate("datumlidgeld").toLocalDate());
		leden.setSoortenledenId(rs.getInt("soortenledenid"));
		leden.setOntvangMail(rs.getBoolean("ontvangmail"));
		leden.setMailVlag(rs.getBoolean("mailvlag"));
		return leden;
	}

}
