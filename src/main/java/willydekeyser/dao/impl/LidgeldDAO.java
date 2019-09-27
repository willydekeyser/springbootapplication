package willydekeyser.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import willydekeyser.dao.ILidgeldDAO;
import willydekeyser.dao.rowmapper.LidgeldLedenRowMapper;
import willydekeyser.dao.rowmapper.LidgeldRowMapper;
import willydekeyser.model.Lidgeld;

@Transactional
@Repository
public class LidgeldDAO implements ILidgeldDAO {

	private final String sql_AllLidgeld = "SELECT * FROM lidgeld ORDER BY lidgeld_id";
	private final String sql_AllLidgeldLeden = "SELECT * FROM lidgeld, ledenlijst WHERE ledenlijstid = ledenlijst_id ORDER BY datum";
	private final String sql_AllLidgeldByLid = "SELECT * FROM lidgeld WHERE ledenlijstid = ? ORDER BY lidgeld_id";
	private final String sql_MAXLidgeldLeden = "SELECT lidgeld.*, ledenlijst.* FROM lidgeld, ledenlijst "
			+ "WHERE (ledenlijst_id = ledenlijstid) AND  (soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3) "
			+ "AND (datum = (SELECT MAX(datum) FROM lidgeld AS lidgeld_sub WHERE ledenlijst_id = lidgeld_sub.ledenlijstid)) "
			+ "ORDER BY datum ASC, lidgeld_id ASC";
	private final String sql_getLidgeldById = "SELECT lidgeld.*, ledenlijst.*  FROM lidgeld, ledenlijst WHERE lidgeld_id = ? AND ledenlijstid = ledenlijst_id";
	
	private final String sql_newLidgeld = "INSERT INTO lidgeld (ledenlijstid, datum, bedrag, createdby, createddate) values (?, ?, ?, ?, ?)";
	private final String sql_updateLidgeld = "UPDATE lidgeld SET ledenlijstid = ?, datum = ?, bedrag = ?, lastmodifiedby = ?, lastmodifieddate = ? WHERE lidgeld_id = ?";
	private final String sql_deleteLidgeld = "DELETE FROM lidgeld WHERE lidgeld_id = ?";
	private final String sql_lidgeldExists = "SELECT EXIST(SELECT * FROM lidgeld WHERE lidgeld_id = ?)";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Lidgeld> getAllLidgeld() {
		return jdbcTemplate.query(sql_AllLidgeld, new LidgeldRowMapper());
	}
	
	@Override
	public List<Lidgeld> getAllLidgeldLeden() {
		return jdbcTemplate.query(sql_AllLidgeldLeden, new LidgeldLedenRowMapper());
	}
	
	@Override
	public List<Lidgeld> getAllLidgelByLid(int id) {
		return jdbcTemplate.query(sql_AllLidgeldByLid, new LidgeldRowMapper(), id);
	}
	
	@Override
	public List<Lidgeld> getMAXLidgeldLeden() {
		return jdbcTemplate.query(sql_MAXLidgeldLeden, new LidgeldLedenRowMapper());
	}

	@Override
	public Lidgeld getLidgeldById(int id) {
		return jdbcTemplate.queryForObject(sql_getLidgeldById, new LidgeldLedenRowMapper(), id);
	}

	@Override
	public Lidgeld addLidgeld(Lidgeld lidgeld) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_newLidgeld, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, lidgeld.getLeden().getId());
				ps.setDate(2, java.sql.Date.valueOf(lidgeld.getDatum()));
				ps.setBigDecimal(3, lidgeld.getBedrag());
				ps.setString(4, authentication.getName());
			    ps.setDate(5, date);
				return ps;
			}
		}, key);
		
		if (key.getKeys().size() > 1) {
			lidgeld.setId(((Number) key.getKeys().get("lidgeld_id")).intValue());
	    } else {
	    	lidgeld.setId(key.getKey().intValue());
	    }
		
		return lidgeld;
	}

	@Override
	public Lidgeld updateLidgeld(Lidgeld lidgeld) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		int key = jdbcTemplate.update(sql_updateLidgeld,lidgeld.getLeden().getId(), lidgeld.getDatum(), lidgeld.getBedrag(), authentication.getName(), date, lidgeld.getId());
		if (key == 1) return lidgeld;
		return null;
	}

	@Override
	public Integer deleteLidgeld(int id) {
		jdbcTemplate.update(sql_deleteLidgeld, id);
		return id;
	}

	@Override
	public boolean lidgeldExists(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_lidgeldExists, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
}
