package willydekeyser.dao.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class KasboekCounterExtractor implements ResultSetExtractor<Integer>{

	@Override
	public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
		Integer counter = 0;
		while (rs.next()) {
			counter = rs.getInt("count");
		}
		return counter;
	}

}
