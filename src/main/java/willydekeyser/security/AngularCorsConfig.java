package willydekeyser.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AngularCorsConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("AngularCorsConfig: ");
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		.allowedHeaders("Header1", "X-Forwarded-Host", "X-Forwarded-Port", 
				"X-Forwarded-Proto", "Origin", "Accept", "Authorization", 
				"Content-Type", "XSRF-Token", "CSRF-Token", "X-CSRF-Token", "same-origin",
				"X-XSRF-TOKEN", "Access-Control-Allow-Origin", "X-Requested-With", "X-Auth-Token")
		.maxAge(60)
		.allowCredentials(true);
	}
}
