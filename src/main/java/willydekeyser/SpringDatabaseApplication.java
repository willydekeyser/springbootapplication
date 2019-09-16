package willydekeyser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import willydekeyser.customproperties.CustomPropertiesService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true
		)
@ComponentScan
@ServletComponentScan
@SpringBootApplication
@EnableAsync
//@EnableSwagger2
public class SpringDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseApplication.class, args);		
	}
	
	@Autowired
	CustomPropertiesService customPropertiesService;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadCustomProperties() {
		customPropertiesService.readCustomProperties();
	}
		
}
