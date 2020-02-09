package willydekeyser.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

	@Bean(name = "masterDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource1")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "secondDataSource")
	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "jdbcMaster")
	@Primary
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource dataSourceMaster) {
        return new JdbcTemplate(dataSourceMaster);
    }

    @Bean(name = "jdbcSecond")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondDataSource") DataSource dataSourceSecondary) {
        return new JdbcTemplate(dataSourceSecondary);
    }
}
