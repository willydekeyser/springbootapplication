package willydekeyser.component;

import java.nio.file.attribute.UserPrincipal;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;



@Component("LogoutListener")
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		for (SecurityContext securityContext : event.getSecurityContexts()) {
            Authentication authentication = securityContext.getAuthentication();
            UserPrincipal user = (UserPrincipal) authentication.getPrincipal(); // UserPrincipal is my custom Principal class
            System.out.println("Session expired!" + user.getName());
            
        }
		
	}

	
}
