package willydekeyser.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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

import willydekeyser.dao.ILidgeldDAO;
import willydekeyser.dao.rowmapper.LidgeldLedenRowMapper;
import willydekeyser.dao.rowmapper.LidgeldRowMapper;
import willydekeyser.model.Lidgeld;

@Transactional
@Repository
public class LidgeldDAO implements ILidgeldDAO {

	private final String sql_AllLidgeld = "SELECT * FROM lidgeld ORDER BY lidgeld_id";
	private final String sql_AllLidgeldLeden = "SELECT * FROM lidgeld, leden WHERE ledenid = leden_id ORDER BY datum";
	private final String sql_AllLidgeldByLid = "SELECT * FROM lidgeld WHERE ledenid = ? ORDER BY lidgeld_id";
	private final String sql_MAXLidgeldLeden = "SELECT lidgeld.*, leden.* FROM lidgeld, leden "
			+ "WHERE (leden_id = ledenid) AND  (soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3) "
			+ "AND (datum = (SELECT MAX(datum) FROM lidgeld lidgeld_sub WHERE leden_id = lidgeld_sub.ledenid)) "
			+ "ORDER BY datum ASC, lidgeld_id ASC";
	private final String sql_getLidgeldById = "SELECT lidgeld.*, leden.*  FROM lidgeld, leden WHERE lidgeld_id = ? AND ledenid = leden_id";
	
	private final String sql_newLidgeld = "INSERT INTO lidgeld (ledenid, datum, bedrag, createdby, createddate) values (?, ?, ?, ?, ?)";
	private final String sql_updateLidgeld = "UPDATE lidgeld SET ledenid = ?, datum = ?, bedrag = ?, lastmodifiedby = ?, lastmodifieddate = ? WHERE lidgeld_id = ?";
	private final String sql_deleteLidgeld = "DELETE FROM lidgeld WHERE lidgeld_id = ?";
	private final String sql_lidgeldExists = "SELECT COUNT(*) FROM lidgeld WHERE EXISTS(SELECT * FROM lidgeld WHERE lidgeld_id = ?) AND ROWNUM = 1";
	
	private LocalDate date = null;
	
	@Autowired
	@Qualifier("jdbcMaster")
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
	public List<Lidgeld> saveLidgeld(List<Lidgeld> lidgeldlijst) {
		return null;
	}
	
	@Override
	public Lidgeld addLidgeld(Lidgeld lidgeld) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		date = LocalDate.now();
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_newLidgeld, new String [] {"lidgeld_id"});
				ps.setInt(1, lidgeld.getLeden().getLeden_id());
				ps.setDate(2, java.sql.Date.valueOf(lidgeld.getDatum()));
				ps.setBigDecimal(3, lidgeld.getBedrag());
				ps.setString(4, authentication.getName());
			    ps.setDate(5, java.sql.Date.valueOf(date));
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
		date = LocalDate.now();
		int key = jdbcTemplate.update(sql_updateLidgeld,lidgeld.getLeden().getLeden_id(), lidgeld.getDatum(), lidgeld.getBedrag(), authentication.getName(), java.sql.Date.valueOf(date), lidgeld.getId());
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
