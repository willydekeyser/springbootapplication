package willydekeyser.component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import willydekeyser.loggers.FileLoggers;

@Component
public class LoginListeners implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
	FileLoggers fileLogger;

	@Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
        String data = "LOGIN:" + "\n  Usernaam: " + user.getUsername() + 
        "\n  Roles: " + user.getAuthorities().toString() + 
        "\n  Datum: " + dateFormat.format(new Date()) + "\n\n";
        try {
			fileLogger.schrijfDataToFile(data);
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
    }
	
}
