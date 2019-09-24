package willydekeyser.admin;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationApplicationListener {
	
	@EventListener
	public void handleSessionDestroyedEvent(SessionDestroyedEvent event) {
				
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

	        System.out.println("Session Destroyed Event: " + event.getId() + " - " + auth.getName() + "\n\n");
	  }
	}
}
