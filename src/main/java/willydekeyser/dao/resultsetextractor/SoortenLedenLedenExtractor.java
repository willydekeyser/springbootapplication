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
import willydekeyser.model.SoortenLeden;

public class SoortenLedenLedenExtractor implements ResultSetExtractor<List<SoortenLeden>>{

	@Override
	public List<SoortenLeden> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, SoortenLeden> map = new LinkedHashMap<Integer, SoortenLeden>();
		List<Leden> ledenlijst = new ArrayList<>();
		while (rs.next()) {
			int soortenLedenId = rs.getInt("soortenleden_id");
			SoortenLeden soortenLeden = map.get(soortenLedenId);
			if(soortenLeden == null) {
				soortenLeden = new SoortenLeden();
				ledenlijst = new ArrayList<>();
				soortenLeden.setId(soortenLedenId);
				soortenLeden.setSoortenleden(rs.getString("soortenleden"));
				map.put(soortenLedenId, soortenLeden);	
			}
			int ledenId = rs.getInt("leden_id");
			if (ledenId > 0) {
				Leden leden = new Leden();
				leden.setLeden_id(ledenId);
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
				leden.setSoortenledenid(rs.getInt("soortenledenid"));
				leden.setSoortenleden(new SoortenLeden(rs.getInt("soortenleden_id"), rs.getString("soortenleden")));
				leden.setOntvangmail(rs.getBoolean("ontvangmail"));
				leden.setMailvlag(rs.getBoolean("mailvlag"));
				ledenlijst.add(leden);
				soortenLeden.setLeden(ledenlijst);
			}
		}
		return new ArrayList<SoortenLeden>(map.values());
	}	
}
