package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Kasboek;
import willydekeyser.model.Rubriek;

public class RubriekKasboekExtractor implements ResultSetExtractor<List<Rubriek>>{

	@Override
	public List<Rubriek> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Map<Integer, Rubriek> map = new LinkedHashMap<Integer, Rubriek>();
		List<Kasboek> kasboeklijst = new ArrayList<>();
		while (rs.next()) {
			int rubriekId = rs.getInt("rubriek_id");
			
			Rubriek rubriek = map.get(rubriekId);
			if(rubriek == null) {
				rubriek = new Rubriek();
				kasboeklijst = new ArrayList<>();
				rubriek.setId(rubriekId);
				rubriek.setRubriek(rs.getString("rubriek"));
				map.put(rubriekId, rubriek);	
			}
			int kasboekId = rs.getInt("kasboek_id");
			if (kasboekId > 0) {
				Kasboek kasboek = new Kasboek();
				kasboek.setId(kasboekId);
				kasboek.setRubriek(new Rubriek(rs.getInt("rubriek_id"), rs.getString("rubriek")));
				kasboek.setJaartal(rs.getInt("jaartal"));
				kasboek.setRubriekId(rs.getInt("rubriek_id"));
				kasboek.setOmschrijving(rs.getString("omschrijving"));
				kasboek.setDatum(rs.getDate("datum").toLocalDate());
				kasboek.setUitgaven(rs.getBigDecimal("uitgaven"));
				kasboek.setInkomsten(rs.getBigDecimal("inkomsten"));
				kasboeklijst.add(kasboek);
				rubriek.setKasboeken(kasboeklijst);
			}
		}
		return new ArrayList<Rubriek>(map.values());
	}	
}
