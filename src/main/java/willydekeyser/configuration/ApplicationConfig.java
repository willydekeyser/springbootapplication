package willydekeyser.configuration;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import willydekeyser.backup.BackUpLijsten;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig implements WebMvcConfigurer {

	@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}
	
	@Bean
	public BackUpLijsten backupLijsten() {
		return new BackUpLijsten();
	}
		
//	@Autowired
//	private CustomInterceptor customInterceptor;
//	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(customInterceptor);
//	}

}
