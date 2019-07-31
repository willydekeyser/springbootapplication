package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import willydekeyser.model.Kasboek;
import willydekeyser.model.Rubriek;

public class RubriekByIdKasboekExtractor implements ResultSetExtractor<Rubriek>{

	@Override
	public Rubriek extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		Rubriek rubriek = new Rubriek();
		List<Kasboek> kasboeklijst = new ArrayList<>();
		Boolean first = true;
		while (rs.next()) {
			if(first) {
				rubriek = new Rubriek();
				kasboeklijst = new ArrayList<>();
				rubriek.setId(rs.getInt("rubriek_id"));
				rubriek.setRubriek(rs.getString("rubriek"));
				first = false;
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
		return rubriek;
	}
}
