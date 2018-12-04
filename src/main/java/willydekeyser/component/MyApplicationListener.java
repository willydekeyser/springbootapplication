package willydekeyser.component;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

//@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent>{

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("MyApplicationListener: " + event.toString());
		
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void name() {
		System.out.println("--------------------------- ApplicationReadyEvent.class -------------------------------------------------------");
	}

}
