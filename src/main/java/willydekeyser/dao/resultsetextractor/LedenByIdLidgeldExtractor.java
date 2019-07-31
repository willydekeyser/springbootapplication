package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;
import willydekeyser.model.SoortenLeden;

public class LedenByIdLidgeldExtractor implements ResultSetExtractor<Leden>{

	@Override
	public Leden extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Leden leden = new Leden();
		List<Lidgeld> lidgeldlijst = new ArrayList<>();
		Boolean first = true;
		while (rs.next()) {
			if(first) {
				leden = new Leden();
				lidgeldlijst = new ArrayList<>();
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
				leden.setSoortenleden(new SoortenLeden(rs.getInt("soortenleden_id"), rs.getString("soortenleden")));
				leden.setOntvangMail(rs.getBoolean("ontvangmail"));
				leden.setMailVlag(rs.getBoolean("mailvlag"));
				first = false;
			}
			int lidgeldId = rs.getInt("lidgeld_id");
			if (lidgeldId > 0) {
				Lidgeld lidgeld = new Lidgeld();
				lidgeld.setId(lidgeldId);
				lidgeld.setLedenId(rs.getInt("ledenlijstid"));
				lidgeld.setDatum(rs.getDate("datum").toLocalDate());
				lidgeld.setBedrag(rs.getBigDecimal("bedrag"));
				lidgeldlijst.add(lidgeld);
				leden.setLidgelden(lidgeldlijst);
			}
		}
		return leden;
	}	
}
