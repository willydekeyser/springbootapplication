package willydekeyser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import willydekeyser.customproperties.CustomPropertiesService;

@EnableWebSecurity
@ComponentScan
@ServletComponentScan
@SpringBootApplication
@EnableAsync
@EnableScheduling
@PropertySources({
	@PropertySource("file:properties/application.properties")
})
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


