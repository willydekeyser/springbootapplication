package willydekeyser.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Service;

import willydekeyser.loggers.FileLoggers;

@Service
public class AuthenticationApplicationListener {
	
	@Autowired
	FileLoggers fileLogger;
	
	@EventListener
	public void handleSessionDestroyedEvent(SessionDestroyedEvent event) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
	    for (SecurityContext securityContext : lstSecurityContext) {
	        //Try to find out, if this event is caused by a logout,
	        //This is true, when the old session has been an authenticated one.
	        Authentication auth = securityContext.getAuthentication();
	        if (auth == null ||
	            !auth.isAuthenticated() ||
	            auth instanceof AnonymousAuthenticationToken) {
	            return;
	        }

	        String data = "Session Destroyed Event: " + 
	        "\n  Session Id: " + event.getId() + 
	        "\n  Usernaam: " + auth.getName() + 
	        "\n  Datum: " + dateFormat.format(new Date()) + "\n\n";
	        try {
				fileLogger.schrijfDataToFile(data);
			} catch (IOException e) {
				System.err.println("Fout: " + e.getMessage());
			}
	  }
	}
}
