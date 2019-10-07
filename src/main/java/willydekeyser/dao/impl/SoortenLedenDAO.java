package willydekeyser.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private final String sql_getSoortenledenById = "SELECT soortenleden.*, ledenlijst.* "
			+ "FROM soortenleden LEFT JOIN ledenlijst ON soortenleden_id = ledenlijst.soortenledenid WHERE soortenleden_id = ?";
	private final String sql_getAllSoortenLedenLeden = "SELECT soortenleden.*, ledenlijst.* "
			+ "FROM soortenleden LEFT JOIN ledenlijst ON soortenleden_id = soortenledenid ORDER BY soortenleden_id";
	private final String sql_addSoortenleden = "INSERT INTO soortenleden (soortenleden, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (?, ?, ?, ?, ?)";
	private final String sql_updateSoortenleden = "UPDATE soortenleden SET Soortenleden = ?, lastmodifiedby = ?, lastmodifieddate = ? WHERE soortenleden_id = ?";
	private final String sql_deleteSoortenleden = "DELETE FROM soortenleden WHERE soortenleden_id = ?";
	private final String sql_soortenledenExists = "SELECT count(*) FROM soortenleden WHERE soortenleden_id = ?";
	private final String sql_ledenExistsBySoortenledenId = "SELECT EXISTS(SELECT * FROM ledenlijst WHERE soortenledenid = ?)";
	
	@Autowired
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
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		jdbcTemplate.batchUpdate(sql_addSoortenleden, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				SoortenLeden soortenLeden = soortenledenlijst.get(index);
				ps.setString(1, soortenLeden.getSoortenleden());
			    ps.setString(2, authentication.getName());
			    ps.setDate(3, date);
			    ps.setString(4, authentication.getName());
			    ps.setDate(5, date);
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
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addSoortenleden, Statement.RETURN_GENERATED_KEYS);
			    ps.setString(1, soortenLeden.getSoortenleden());
			    ps.setString(2, authentication.getName());
			    ps.setDate(3, date);
			    ps.setString(4, authentication.getName());
			    ps.setDate(5, date);
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
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		int key = jdbcTemplate.update(sql_updateSoortenleden, soortenLeden.getSoortenleden(), authentication.getName(), date, soortenLeden.getId());
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
