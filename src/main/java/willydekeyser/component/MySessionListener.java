package willydekeyser.component;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import willydekeyser.loggers.FileLoggers;

@Component
@WebListener
public class MySessionListener implements HttpSessionListener {

	@Autowired
	FileLoggers fileLogger;
	
	private final AtomicInteger counter = new AtomicInteger();
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		String data = "Session is created:" + 
			"\n    ID: " + event.getSession().getId() +
			"\n    Max timeout: " + event.getSession().getMaxInactiveInterval() +
			"\n    Date: " + new Date(event.getSession().getCreationTime()) +
			"\n    Date: " + new Date() + "\n\n";
		try {
			fileLogger.schrijfDataToFile(data);
		} catch (IOException e) {
			System.err.println("Fout: " + e.getMessage());
		}
		
		counter.incrementAndGet();  //incrementing the counter
        updateSessionCounter(event);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
				
		String data = "Session is destroyed:" + 
			"\n    ID: " + event.getSession().getId() +
			"\n    Date: " + new Date(event.getSession().getCreationTime()) +
			"\n    TIME: " + new Time(event.getSession().getLastAccessedTime()) +
			"\n    DATE: " + new Date() + "\n\n";
		try {
			fileLogger.schrijfDataToFile(data);
		} catch (IOException e) {
			System.err.println("Fout: " + e.getMessage());
		}
		counter.decrementAndGet();
        updateSessionCounter(event);
	}

	private void updateSessionCounter(HttpSessionEvent httpSessionEvent){
        httpSessionEvent.getSession().getServletContext().setAttribute("activeSession", counter.get());
        String data = "Total active session are: " + counter.get() + "\n\n";
        try {
			fileLogger.schrijfDataToFile(data);
		} catch (IOException e) {
			System.err.println("Fout: " + e.getMessage());
		}
    }
}
