package willydekeyser.component;

import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@Component
public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("==== Session is created ==== " + event.getSession().getId() + " - " + 
				event.getSession().getMaxInactiveInterval() + " - " + 
				new Date(event.getSession().getCreationTime()) + " - " + 
				new Time(event.getSession().getCreationTime()));
		
		HttpSession session = event.getSession();
	    System.out.println("session id: " + session.getId());
	    session.setMaxInactiveInterval(3600);//in seconds
	    System.out.println("==== Session is created ==== " + event.getSession().getId() + " - " + 
	    		event.getSession().getMaxInactiveInterval() + " - " + 
	    		new Date(event.getSession().getCreationTime()) + " - " + 
	    		new Time(event.getSession().getCreationTime()));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("==== Session is destroyed ==== " + 
				event.getSession().getId() + " - " + 
				new Time(event.getSession().getLastAccessedTime()));
	}

}
