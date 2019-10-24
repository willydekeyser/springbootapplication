package willydekeyser.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import willydekeyser.dao.IRubriekDAO;
import willydekeyser.dao.resultsetextractor.RubriekByIdKasboekExtractor;
import willydekeyser.dao.resultsetextractor.RubriekKasboekExtractor;
import willydekeyser.dao.rowmapper.RubriekRowMapper;
import willydekeyser.model.Rubriek;

@Transactional
@Repository
public class RubriekDAO implements IRubriekDAO {

	private final String sql_getAllRubriek = "SELECT * FROM rubriek ORDER BY rubriek_id";
	private final String sql_getAllRubriekKasboek = "SELECT rubriek.*, kasboek.* "
			+ "FROM rubriek LEFT JOIN kasboek ON rubriek_id = rubriekid";
	private final String sql_getRubriekById = "SELECT rubriek.*, kasboek.* "
			+ "FROM rubriek LEFT JOIN kasboek ON rubriek_id = rubriekid WHERE rubriek_id = ?";
	private final String sql_addRubriek = "INSERT INTO rubriek (rubriek, createdby, createddate) VALUES (?, ?, ?)";
	private final String sql_updateRubriek = "UPDATE rubriek SET rubriek = ?, lastmodifiedby = ?, lastmodifieddate = ?  WHERE rubriek_id = ?";
	private final String sql_deleteRubriek = "DELETE FROM rubriek WHERE rubriek_id = ?";
	private final String sql_rubriekExists = "SELECT EXIST(SELECT * FROM rubriek WHERE rubriek_id = ?)";
	private final String sql_kasboekExistsByRuriekId = "SELECT EXISTS(SELECT * FROM kasboek WHERE rubriekid = ?)";
	
	@Autowired
	@Qualifier("jdbcMaster")
    private JdbcTemplate masterJdbcTemplate;

	@Override
	public List<Rubriek> getAllRubriek() {
		return masterJdbcTemplate.query(sql_getAllRubriek, new RubriekRowMapper());
	}
	
	@Override
	public List<Rubriek> getAllRubriekKasboek() {
		return masterJdbcTemplate.query(sql_getAllRubriekKasboek, new RubriekKasboekExtractor());
	}

	@Override
	public Rubriek getRubriekById(int id) {
		return masterJdbcTemplate.query(sql_getRubriekById, new RubriekByIdKasboekExtractor(), id);
	}

	@Override
	public List<Rubriek> saveRubriek(List<Rubriek> rubrieklijst) {
		return null;
	}
	
	@Override
	public Rubriek addRubriek(Rubriek rubriek) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		KeyHolder key = new GeneratedKeyHolder();
		masterJdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addRubriek, Statement.RETURN_GENERATED_KEYS);
			    ps.setString(1, rubriek.getRubriek());
			    ps.setString(2, authentication.getName());
			    ps.setDate(3, date);
			    return ps;
			}
		}, key);
		
	    if (key.getKeys().size() > 1) {
	    	rubriek.setId(((Number) key.getKeys().get("rubriek_id")).intValue());
	    } else {
	    	rubriek.setId(key.getKey().intValue());
	    }
	    return rubriek;
	}

	@Override
	public Rubriek updateRubriek(Rubriek rubriek) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		int key = masterJdbcTemplate.update(sql_updateRubriek, rubriek.getRubriek(), authentication.getName(), date, rubriek.getId());
		if (key == 1) return rubriek;
		return null;
	}

	@Override
	public void deleteRubriek(int id) {
		masterJdbcTemplate.update(sql_deleteRubriek, id);
	}

	@Override
	public Boolean rubriekExists(int id) {
		return masterJdbcTemplate.queryForObject(sql_rubriekExists, Boolean.class, id);
	}
	
	@Override
	public Boolean kasboekExistsByRubriekId(int id) {
		return masterJdbcTemplate.queryForObject(sql_kasboekExistsByRuriekId, Boolean.class, id);
	}
	
}
