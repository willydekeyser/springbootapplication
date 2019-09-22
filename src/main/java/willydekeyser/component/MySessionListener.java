package willydekeyser.component;

import java.sql.Time;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

@Component
public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		System.out.println("\n\n ==== Session is created ==== " + 
			"\n ID: " + event.getSession().getId() + " - " + 
			"\n Max timeout: " + event.getSession().getMaxInactiveInterval() + " - " + 
			"\n Date: " + new Date(event.getSession().getCreationTime()) + " - " + 
			"\n TIME: " + new Time(event.getSession().getCreationTime()) + " - " + new Date() + "\n\n");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		System.out.println("\n\n ==== Session is destroyed ==== " + 
			"\n ID: " + event.getSession().getId() + " - " + 
			"\n Date: " + new Date(event.getSession().getCreationTime()) + " - " + 
			"\n TIME: " + new Time(event.getSession().getLastAccessedTime()) + " - " + new Date() + "\n\n");
	}

}
