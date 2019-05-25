package willydekeyser.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("Config: SecurityConfig " + httpSecurity.toString());
	}

}