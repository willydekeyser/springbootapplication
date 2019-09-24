package willydekeyser.component;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@Component
@WebListener
public class MySessionListener implements HttpSessionListener {

	private final AtomicInteger counter = new AtomicInteger();
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		System.out.println("\n\n ==== Session is created ==== " + 
			"\n ID: " + event.getSession().getId() + " - " + 
			"\n Max timeout: " + event.getSession().getMaxInactiveInterval() + " - " + 
			"\n Date: " + new Date(event.getSession().getCreationTime()) + " - " + 
			"\n Date: " + new Date() + "\n\n");
		
		counter.incrementAndGet();  //incrementing the counter
        updateSessionCounter(event);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
				
		System.out.println("\n\n ==== Session is destroyed ==== " + 
			"\n ID: " + event.getSession().getId() + " - " + 
			"\n Date: " + new Date(event.getSession().getCreationTime()) + " - " + 
			"\n TIME: " + new Time(event.getSession().getLastAccessedTime()) + " - " + 
			"\n DATE: " + new Date() + "\n\n");
		
		counter.decrementAndGet();
        updateSessionCounter(event);
	}

	private void updateSessionCounter(HttpSessionEvent httpSessionEvent){
        httpSessionEvent.getSession().getServletContext().setAttribute("activeSession", counter.get());
        System.out.println("Total active session are: " + counter.get() + "\n\n");
    }
}
