package willydekeyser.component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import willydekeyser.loggers.FileLoggers;


@Service
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	FileLoggers fileLogger;
	
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		for (SecurityContext securityContext : event.getSecurityContexts()) {
            Authentication authentication = securityContext.getAuthentication();
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String data = "Session expired:" + 
            "\n		Usernaam: " + user.getUsername() +
            "\n		Datum: " + dateFormat.format(new Date()) + "\n\n";
            try {
				fileLogger.schrijfDataToFile(data);
			} catch (IOException e) {
				System.err.println("Fout: " + e.getMessage());
			}
        }
		
	}

	
}
