package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Leden;
import willydekeyser.model.SoortenLeden;

public class SoortenLedenByIdLedenExtractor implements ResultSetExtractor<SoortenLeden>{

	@Override
	public SoortenLeden extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		SoortenLeden soortenLeden = new SoortenLeden();
		List<Leden> ledenlijst = new ArrayList<>();
		Boolean first = true;
		while (rs.next()) {
			if(first) {
				soortenLeden = new SoortenLeden();
				ledenlijst = new ArrayList<>();
				soortenLeden.setId(rs.getInt("soortenleden_id"));
				soortenLeden.setSoortenleden(rs.getString("soortenLeden"));
				first = false;	
			}
			int ledenId = rs.getInt("ledenlijst_id");
			if (ledenId > 0) {
				Leden leden = new Leden();
				leden.setLedenlijst_id(ledenId);
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
				leden.setSoortenledenid(rs.getInt("soortenleden_id"));
				leden.setSoortenleden(new SoortenLeden(rs.getInt("soortenleden_id"), rs.getString("soortenleden")));
				leden.setOntvangmail(rs.getBoolean("ontvangmail"));
				leden.setMailvlag(rs.getBoolean("mailvlag"));
				ledenlijst.add(leden);
				soortenLeden.setLeden(ledenlijst);
			}
		}
		return soortenLeden;
	}	
}
