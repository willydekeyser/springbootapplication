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

	private final String sql_getAllLeden = "SELECT * FROM leden ORDER BY leden_id" ;
	private final String sql_getAllIedereenlijst = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden "
			+ "WHERE soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3 OR soortenledenid = 4 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getLedennamenlijstSoort = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden, soortenleden "
			+ "WHERE leden.soortenledenid =  soortenleden.soortenleden_id AND soortenleden.soortenleden LIKE ? "
			+ "ORDER BY leden.familienaam, leden.voornaam" ;
	private final String sql_getLedennamenlijstSoortIedereen = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden "
			+ "ORDER BY leden.familienaam, leden.voornaam" ;
	private final String sql_getAllLedenlijst = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden "
			+ "WHERE soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllBestuursledenlijst = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden "
			+ "WHERE soortenledenid = 3 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllWerkendeledenlijst = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden "
			+ "WHERE soortenledenid = 2 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllGeenledenlijst = "SELECT leden_id, voornaam, familienaam, emailadres "
			+ "FROM leden "
			+ "WHERE soortenledenid = 4 "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllLedenSoortenleden = "SELECT leden.*, soortenleden.* "
			+ "FROM leden, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllWerkendeLedenSoortenleden = "SELECT leden.*, soortenleden.* "
			+ "FROM leden, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 2) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllBestuursLedenSoortenleden = "SELECT leden.*, soortenleden.* "
			+ "FROM leden, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 3) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllGeenLedenSoortenleden = "SELECT leden.*, soortenleden.* "
			+ "FROM leden, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 4) "
			+ "ORDER BY familienaam, voornaam" ;
	private final String sql_getAllIedereenSoortenleden = "SELECT leden.*, soortenleden.* "
			+ "FROM leden, soortenleden "
			+ "WHERE soortenledenid = soortenleden_id "
			+ "AND (soortenledenid = 1 OR soortenledenid = 2 OR soortenledenid = 3 OR soortenledenid = 4) "
			+ "ORDER BY Familienaam" ;
	private final String sql_getAllLedenLidgeld = "SELECT leden.*, lidgeld.* "
			+ "FROM leden "
			+ "LEFT JOIN lidgeld ON leden_id = ledenid";
	private final String sql_getLedenById = "SELECT leden.*, lidgeld.*, soortenleden.* FROM leden "
			+ "LEFT JOIN lidgeld ON leden_id = ledenid "
			+ "LEFT JOIN soortenleden ON soortenledenid = soortenleden_id "
			+ "WHERE leden_id = ? "
			+ "ORDER BY leden_id, lidgeld_id";
	
	private final String sql_addLeden = "INSERT INTO leden (voornaam, familienaam, straat, nr, postnr, "
			+ "gemeente, telefoonnummer, gsmnummer, emailadres, webadres, datumlidgeld, soortenledenid, "
			+ "ontvangmail, mailvlag, createdby, createddate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String sql_updateLeden = "UPDATE leden SET voornaam = ?, familienaam = ?, straat = ?, nr = ?, postnr = ?, "
			+ "gemeente = ?, telefoonnummer = ?, gsmnummer = ?, emailadres = ?, webadres = ?, datumlidgeld = ?, soortenledenid = ?, "
			+ "ontvangmail = ?, mailvlag = ?, lastmodifiedby = ?, lastmodifieddate = ? WHERE leden_id = ?";
	private final String sql_deleteLeden = "DELETE FROM leden WHERE leden_id = ?";
	private final String sql_ledenExists = "SELECT COUNT(*) FROM leden WHERE EXISTS(SELECT * FROM leden WHERE leden_id = ?) AND ROWNUM = 1";
	
	private LocalDate date = null;
	
	@Autowired
	@Qualifier("jdbcMaster")
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
	public List<Leden> getLedenNamenlijstSoort(String soort) {
		if (soort.equals("Iedereen")) {
			return jdbcTemplate.query(sql_getLedennamenlijstSoortIedereen, new LedenNamenRowMapper());
		}
		return jdbcTemplate.query(sql_getLedennamenlijstSoort, new LedenNamenRowMapper(), "%" + soort);
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
	public List<Leden> saveLeden(List<Leden> ledenlijst) {
		return null;
	}
	
	@Override
	public Leden addLeden(Leden leden) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		date = LocalDate.now();
		KeyHolder key = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(sql_addLeden, new String [] {"leden_id"});
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
				ps.setBoolean(13, leden.isOntvangmail());
				ps.setBoolean(14, leden.isMailvlag());
				ps.setString(15, authentication.getName());
			    ps.setDate(16, java.sql.Date.valueOf(date));
				return ps;
			}
		}, key);
		
		if (key.getKeys().size() > 1) {
			leden.setLeden_id(((Number) key.getKeys().get("leden_id")).intValue());
	    } else {
	    	leden.setLeden_id(key.getKey().intValue());
	    }
		
		return leden;
	}

	@Override
	public Leden updateLeden(Leden leden) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		date = LocalDate.now();
		jdbcTemplate.update(sql_updateLeden, leden.getVoornaam(), leden.getFamilienaam(), leden.getStraat(), leden.getNr(), 
				leden.getPostnr(), leden.getGemeente(),leden.getTelefoonnummer(), leden.getGsmnummer(), leden.getEmailadres(), 
				leden.getWebadres(), leden.getDatumlidgeld(), leden.getSoortenleden().getId(), leden.isOntvangmail(), 
				leden.isMailvlag(), authentication.getName(), java.sql.Date.valueOf(date), leden.getLeden_id());
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
