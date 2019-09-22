package willydekeyser.component;

import java.io.IOException;

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
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
        UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
        System.out.println("LOGIN name: " + user.getUsername() + " - " + user.getAuthorities().toString());
        try {
			fileLogger.schrijfDataToFile("LOGIN name: " + user.getUsername() + " - " + user.getAuthorities().toString());
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
    }
	
}
