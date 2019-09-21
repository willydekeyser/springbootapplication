package willydekeyser.component;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

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

	@EventListener(ServletRequestHandledEvent.class)
	public void servletRequestHandledEvent() {
		System.out.println("--------------------------- ServletRequestHandledEvent.class -------------------------------------------------------");
	}
	
	@EventListener
	  public void handleEvent (RequestHandledEvent event) {
	      System.out.println("-- RequestHandledEvent --");
	      System.out.println(event.getUserName() + " - " + event.getShortDescription());
	  }
}
