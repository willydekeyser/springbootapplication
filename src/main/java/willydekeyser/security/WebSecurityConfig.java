package willydekeyser.security;

import static willydekeyser.controller.NamenLijst.ROLE_ADMIN;
import static willydekeyser.controller.NamenLijst.ROLE_GOLD;
import static willydekeyser.controller.NamenLijst.ROLE_USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import willydekeyser.filters.ComputerclubLogoutSuccessHandler;

@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider customAuthentiocationProvider;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	BCryptPasswordEncoder paswoordencoder;
	
	@Autowired
	private ComputerclubAuthenticationSuccessHandler successHandler;
	
	@Autowired
	private ComputerclubLogoutSuccessHandler computerLogoutSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("Config: WebSecurityConfig " + httpSecurity.toString());
		httpSecurity.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/kasboek/**").hasRole(ROLE_USER)
			.antMatchers("/lidgeld/**").hasRole(ROLE_USER)
			.antMatchers("/leden/**").hasRole(ROLE_USER)
			.antMatchers("/rubriek/**").hasRole(ROLE_USER)
			.antMatchers("/soortenleden/**").hasRole(ROLE_USER)
			.antMatchers("/restcontroller/**").hasAnyRole(ROLE_GOLD, ROLE_ADMIN)
			.antMatchers("/actuator/**").hasRole(ROLE_GOLD)
			.antMatchers("/agenda/**").hasAnyRole(ROLE_GOLD, ROLE_ADMIN)
			.antMatchers("/rapporten/**").hasAnyRole(ROLE_GOLD, ROLE_ADMIN)
			.and()
			.formLogin().loginPage("/login").successHandler(successHandler).permitAll()
			.and()
			.logout().permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.logoutSuccessHandler(computerLogoutSuccessHandler)
			.and()
			.rememberMe().key("willydekeyser").tokenValiditySeconds(3600)
			.and()
			.exceptionHandling().accessDeniedPage("/error/403")
			.and()
			.csrf();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthentiocationProvider);
		//auth.userDetailsService(userDetailsService);
	}
			
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
			
}
