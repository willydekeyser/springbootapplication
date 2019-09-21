package willydekeyser.component;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;

//@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent>{

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		
		System.out.println("Event: " + event.toString());
		
	}

	
}
