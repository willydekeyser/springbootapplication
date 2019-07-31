package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;

public class LedenLidgeldExtractor implements ResultSetExtractor<List<Leden>>{

	@Override
	public List<Leden> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, Leden> map = new LinkedHashMap<Integer, Leden>();
		List<Lidgeld> lidgeldlijst = new ArrayList<>();
		while (rs.next()) {
			int ledenId = rs.getInt("ledenlijst_id");
			Leden leden = map.get(ledenId);
			if(leden == null) {
				leden = new Leden();
				lidgeldlijst = new ArrayList<>();
				leden.setId(ledenId);
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
				map.put(ledenId, leden);	
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
		return new ArrayList<Leden>(map.values());
	}	
}
