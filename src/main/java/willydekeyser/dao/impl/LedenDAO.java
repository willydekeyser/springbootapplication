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

import willydekeyser.dao.ILedenDAO;
import willydekeyser.dao.resultsetextractor.LedenByIdLidgeldExtractor;
import willydekeyser.dao.resultsetextractor.LedenLidgeldExtractor;
import willydekeyser.dao.rowmapper.LedenNamenRowMapper;
import willydekeyser.dao.rowmapper.LedenRowMapper;
import willydekeyser.dao.rowmapper.LedenSoortenLedenRowMapper;
import willydekeyser.model.Leden;

@Transactional
@Repository
public class LedenDAO implements ILedenDAO {

	private final String sql_getAllLeden = "SELECT * FROM ledenlijst ORDER BY ledenlijst_id" ;
	private final String sql_getAllIedereenlijst = "SELECT ledenlijst_id, voornaam, familienaam, emailadres "
			+ "FROM ledenlijst "
			+ "WHERE soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3 OR soortenledenid = 4 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllLedenlijst = "SELECT ledenlijst_id, voornaam, familienaam, emailadres "
			+ "FROM ledenlijst "
			+ "WHERE soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllBestuursledenlijst = "SELECT ledenlijst_id, voornaam, familienaam, emailadres "
			+ "FROM ledenlijst "
			+ "WHERE soortenledenid = 3 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllWerkendeledenlijst = "SELECT ledenlijst_id, voornaam, familienaam, emailadres "
			+ "FROM ledenlijst "
			+ "WHERE soortenledenid = 2 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllGeenledenlijst = "SELECT ledenlijst_id, voornaam, familienaam, emailadres "
			+ "FROM ledenlijst "
			+ "WHERE soortenledenid = 4 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllLedenSoortenleden = "SELECT ledenlijst.*, soortenleden.* "
			+ "FROM ledenlijst, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllWerkendeLedenSoortenleden = "SELECT ledenlijst.*, soortenleden.* "
			+ "FROM ledenlijst, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 2) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllBestuursLedenSoortenleden = "SELECT ledenlijst.*, soortenleden.* "
			+ "FROM ledenlijst, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 3) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllGeenLedenSoortenleden = "SELECT ledenlijst.*, soortenleden.* "
			+ "FROM ledenlijst, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 4) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllIedereenSoortenleden = "SELECT ledenlijst.*, soortenleden.* "
			+ "FROM ledenlijst, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3 OR soortenledenid = 4) "
			+ "ORDER BY ledenlijst.Familienaam" ;
	private final String sql_getAllLedenLidgeld = "SELECT ledenlijst.*, lidgeld.* "
			+ "FROM ledenlijst "
			+ "LEFT JOIN lidgeld ON ledenlijst_id = ledenlijstid";
	private final String sql_getLedenById = "SELECT ledenlijst.*, lidgeld.*, soortenleden.* FROM ledenlijst "
			+ "LEFT JOIN lidgeld ON ledenlijst_id = ledenlijstid "
			+ "LEFT JOIN soortenleden ON soortenledenid = soortenleden_id "
			+ "WHERE ledenlijst_id = ? "
			+ "ORDER BY ledenlijst_id, lidgeld_id";
	
	private final String sql_addLeden = "INSERT INTO ledenlijst (voornaam, familienaam, straat, nr, postnr, "
			+ "gemeente, telefoonnummer, gsmnummer, emailadres, webadres, datumlidgeld, soortenledenid, "
			+ "ontvangmail, mailvlag, createdby, createddate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String sql_updateLeden = "UPDATE ledenlijst SET voornaam = ?, familienaam = ?, straat = ?, nr = ?, postnr = ?, "
			+ "gemeente = ?, telefoonnummer = ?, gsmnummer = ?, emailadres = ?, webadres = ?, datumlidgeld = ?, soortenledenid = ?, "
			+ "ontvangmail = ?, mailvlag = ?, lastmodifiedby = ?, lastmodifieddate = ? WHERE ledenlijst_id = ?";
	private final String sql_deleteLeden = "DELETE FROM ledenlijst WHERE ledenlijst_id = ?";
	private final String sql_ledenExists = "SELECT EXSIST(SELECT * FROM ledenlijst WHERE ledenlijst_id = ?)";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private String sql;
	
	@Override
	public List<Leden> getAllLeden() {
		return jdbcTemplate.query(sql_getAllLeden, new LedenRowMapper());
	}
	
	@Override
	public List<Leden> getAllLedenNamenlijst(Integer soort) {
		return jdbcTemplate.query(getLedenNamenlijstSQL(soort), new LedenNamenRowMapper());
	}
	
	@Override
	public List<Leden> getAllLedenSoortenleden(Integer soort) {
		return jdbcTemplate.query(getLedenSoortenledenSQL(soort), new LedenSoortenLedenRowMapper());
	}

	@Override
	public List<Leden> getAllLedenLidgeld() {
		return jdbcTemplate.query(sql_getAllLedenLidgeld, new LedenLidgeldExtractor());
	}
	
	@Override
	public Leden getLedenById(int id) {
		return jdbcTemplate.query(sql_getLedenById, new LedenByIdLidgeldExtractor(), id);
	}

	@Override
	public Leden addLeden(Leden leden) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addLeden, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, leden.getVoornaam());
				ps.setString(2, leden.getFamilienaam());
				ps.setString(3, leden.getStraat());
				ps.setString(4, leden.getNr());
				ps.setString(5, leden.getPostnr());
				ps.setString(6, leden.getGemeente());
				ps.setString(7, leden.getTelefoonnummer());
				ps.setString(8, leden.getGsmnummer());
				ps.setString(9, leden.getEmailadres());
				ps.setString(10, leden.getWebadres());
				ps.setDate(11, java.sql.Date.valueOf(leden.getDatumlidgeld()));
				ps.setInt(12, leden.getSoortenleden().getId());
				ps.setBoolean(13, leden.isOntvangMail());
				ps.setBoolean(14, leden.isMailVlag());
				ps.setString(15, authentication.getName());
			    ps.setDate(16, date);
				return ps;
			}
		}, key);
		
		if (key.getKeys().size() > 1) {
			leden.setId(((Number) key.getKeys().get("ledenlijst_id")).intValue());
	    } else {
	    	leden.setId(key.getKey().intValue());
	    }
		
		return leden;
	}

	@Override
	public Leden updateLeden(Leden leden) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Calendar currenttime = Calendar.getInstance();   
		Date date = new Date((currenttime.getTime()).getTime());
		jdbcTemplate.update(sql_updateLeden, leden.getVoornaam(), leden.getFamilienaam(), leden.getStraat(), leden.getNr(), 
				leden.getPostnr(), leden.getGemeente(),leden.getTelefoonnummer(), leden.getGsmnummer(), leden.getEmailadres(), 
				leden.getWebadres(), leden.getDatumlidgeld(), leden.getSoortenleden().getId(), leden.isOntvangMail(), 
				leden.isMailVlag(), authentication.getName(), date, leden.getId());
		return leden;
	}

	@Override
	public Integer deleteLeden(int id) {
		jdbcTemplate.update(sql_deleteLeden, id);
		return id;
	}

	@Override
	public boolean ledenExists(int id) {
		Integer count = jdbcTemplate.queryForObject(sql_ledenExists, Integer.class, id);
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private String getLedenNamenlijstSQL(Integer soort) {
		switch (soort) {
		case 1:
			sql = sql_getAllLedenlijst;
			break;
		case 2:
			sql = sql_getAllWerkendeledenlijst;
			break;
		case 3:
			sql = sql_getAllBestuursledenlijst;
			break;
		case 4:
			sql = sql_getAllGeenledenlijst;
			break;
		case 5:
			sql = sql_getAllIedereenlijst;
			break;
		default:
			sql = sql_getAllLedenlijst;
			break;
		}
		return sql;
	}
	
	private String getLedenSoortenledenSQL(Integer soort) {
		switch (soort) {
		case 1:
			sql = sql_getAllLedenSoortenleden;
			break;
		case 2:
			sql = sql_getAllWerkendeLedenSoortenleden;
			break;
		case 3:
			sql = sql_getAllBestuursLedenSoortenleden;
			break;
		case 4:
			sql = sql_getAllGeenLedenSoortenleden;
			break;
		case 5:
			sql = sql_getAllIedereenSoortenleden;
			break;
		default:
			sql = sql_getAllLedenSoortenleden;
			break;
		}
		return sql;
	}
	
	
}
