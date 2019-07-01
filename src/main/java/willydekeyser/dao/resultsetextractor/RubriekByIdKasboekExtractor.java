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
				rubriek.setId(rs.getInt("Id"));
				rubriek.setRubriek(rs.getString("Rubriek"));
				first = false;
			}
			int kasboekId = rs.getInt("Id");	
			if (kasboekId > 0) {
				Kasboek kasboek = new Kasboek();
				kasboek.setId(kasboekId);
				kasboek.setRubriek(new Rubriek(rs.getInt("rubriekId"), rs.getString("Rubriek")));
				kasboek.setJaartal(rs.getInt("Jaartal"));
				kasboek.setRubriekId(rs.getInt("RubriekId"));
				kasboek.setOmschrijving(rs.getString("Omschrijving"));
				kasboek.setDatum(rs.getDate("Datum").toLocalDate());
				kasboek.setUitgaven(rs.getBigDecimal("Uitgaven"));
				kasboek.setInkomsten(rs.getBigDecimal("Inkomsten"));
				kasboeklijst.add(kasboek);
				rubriek.setKasboeken(kasboeklijst);
			}
		}
		return rubriek;
	}
}
