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
		System.out.println("DataSourceConfig 1");
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "secondDataSource")
	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSource secondDataSource() {
		System.out.println("DataSourceConfig 2");
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "jdbcMaster")
	@Primary
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource dataSourceMaster) {
		System.out.println("DataSourceConfig 3");
        return new JdbcTemplate(dataSourceMaster);
    }

    @Bean(name = "jdbcSecond")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondDataSource") DataSource dataSourceMaster) {
    	System.out.println("DataSourceConfig 4");
        return new JdbcTemplate(dataSourceMaster);
    }
}
