package willydekeyser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import willydekeyser.dao.ISoortenLedenDAO;
import willydekeyser.dao.resultsetextractor.SoortenLedenByIdLedenExtractor;
import willydekeyser.dao.resultsetextractor.SoortenLedenLedenExtractor;
import willydekeyser.dao.rowmapper.SoortenLedenRowMapper;
import willydekeyser.model.SoortenLeden;

@Transactional
@Repository
public class SoortenLedenDAO implements ISoortenLedenDAO {

	private final String sql_getAllSoortenleden = "SELECT soortenleden.* FROM soortenleden ORDER BY soortenleden_id";
	private final String sql_getSoortenledenById = "SELECT soortenleden.*, leden.* "
			+ "FROM soortenleden LEFT JOIN leden ON soortenleden_id = leden.soortenid WHERE soortenleden_id = ?";
	private final String sql_getAllSoortenLedenLeden = "SELECT soortenleden.*, leden.* "
			+ "FROM soortenleden LEFT JOIN leden ON soortenleden_id = soortenledenid ORDER BY soortenleden_id";
	private final String sql_addSoortenleden = "INSERT INTO soortenleden (soortenleden, createdby, createddate) VALUES (?, ?, ?)";
	private final String sql_updateSoortenleden = "UPDATE soortenleden SET Soortenleden = ?, lastmodifiedby = ?, lastmodifieddate = ? WHERE soortenleden_id = ?";
	private final String sql_deleteSoortenleden = "DELETE FROM soortenleden WHERE soortenleden_id = ?";
	private final String sql_soortenledenExists = "SELECT count(*) FROM soortenleden WHERE soortenleden_id = ?";
	private final String sql_ledenExistsBySoortenledenId = "SELECT COUNT (*) FROM soortenleden WHERE EXISTS(SELECT * FROM leden WHERE soortenledenid = ?) AND ROWNUM = 1";
	
	private LocalDate date = null;
	
	@Autowired
	@Qualifier("jdbcMaster")
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SoortenLeden> getAllSoortenLeden() {
		return jdbcTemplate.query(sql_getAllSoortenleden, new SoortenLedenRowMapper());
	}
	
	@Override
	public List<SoortenLeden> getAllSoortenLedenLeden() {
		return jdbcTemplate.query(sql_getAllSoortenLedenLeden, new SoortenLedenLedenExtractor());
	}

	@Override
	public SoortenLeden getSoortenLedenById(int id) {
		return  jdbcTemplate.query(sql_getSoortenledenById, new SoortenLedenByIdLedenExtractor(), id);
	}

	@Override
	public List<SoortenLeden> saveSoortenleden(List<SoortenLeden> soortenledenlijst) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		date = LocalDate.now();
		jdbcTemplate.batchUpdate(sql_addSoortenleden, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				SoortenLeden soortenLeden = soortenledenlijst.get(index);
				ps.setString(1, soortenLeden.getSoortenleden());
			    ps.setString(2, authentication.getName());
			    ps.setDate(3, java.sql.Date.valueOf(date));
			}
			
			@Override
			public int getBatchSize() {
				return soortenledenlijst.size();
			}
		});
		return jdbcTemplate.query(sql_getAllSoortenleden, new SoortenLedenRowMapper());
	}
	
	@Override
	public SoortenLeden addSoortenLeden(SoortenLeden soortenLeden) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		date = LocalDate.now();
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addSoortenleden, new String [] {"soortenleden_id"});
			    ps.setString(1, soortenLeden.getSoortenleden());
			    ps.setString(2, authentication.getName());
			    ps.setDate(3, java.sql.Date.valueOf(date));
			    return ps;
			}
		}, key);
		if (key.getKeys().size() > 1) {
			soortenLeden.setId(((Number) key.getKeys().get("soortenleden_id")).intValue());
	    } else {
	    	soortenLeden.setId(key.getKey().intValue());
	    }
	    return soortenLeden;
	}

	@Override
	public SoortenLeden updateSoortenLeden(SoortenLeden soortenLeden) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		date = LocalDate.now();
		int key = jdbcTemplate.update(sql_updateSoortenleden, soortenLeden.getSoortenleden(), authentication.getName(), java.sql.Date.valueOf(date), soortenLeden.getId());
		if (key == 1) return soortenLeden;
		return null;
	}

	@Override
	public void deleteSoortenLeden(int id) {
		jdbcTemplate.update(sql_deleteSoortenleden, id);
	}

	@Override
	public Boolean soortenLedenExists(int id) {
		return jdbcTemplate.queryForObject(sql_soortenledenExists, Boolean.class, id);
	}
	
	@Override
	public Boolean ledenExistsBySoortenledenId(int id) {
		return jdbcTemplate.queryForObject(sql_ledenExistsBySoortenledenId, Boolean.class, id);
	}
}
