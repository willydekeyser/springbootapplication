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
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan
@ServletComponentScan
@SpringBootApplication
@EnableAsync
public class SpringDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseApplication.class, args);		
	}
	
	@Autowired
	CustomPropertiesService customPropertiesService;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadCustomProperties() {
		//customPropertiesService.readCustomProperties();
	}
	
//	@Bean
//	  public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//	    return args -> {
//
//	      System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//	      String[] beanNames = ctx.getBeanDefinitionNames();
//	      Arrays.sort(beanNames);
//	      for (String beanName : beanNames) {
//	        System.out.println(beanName);
//	      }
//	    };
//	  }    
	
}
