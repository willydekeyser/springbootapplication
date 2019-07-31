package willydekeyser.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import willydekeyser.model.SoortenLeden;

public class SoortenLedenRowMapper implements RowMapper<SoortenLeden>{

	@Override
	public SoortenLeden mapRow(ResultSet rs, int rowNum) throws SQLException {

		SoortenLeden soortenLeden = new SoortenLeden();
		soortenLeden.setId(rs.getInt("soortenleden_id"));
		soortenLeden.setSoortenleden(rs.getString("soortenleden"));
		return soortenLeden;
	}

}
