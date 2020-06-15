package willydekeyser.component;

import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Service;

@Service
public class MyTimeoutFilter implements ApplicationListener<ApplicationEvent>{

	public MyTimeoutFilter() {
        super();
        System.out.println("Application context listener is created!");
    }

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof SessionDestroyedEvent) {
            SessionDestroyedEvent sdEvent = (SessionDestroyedEvent) event;
            List<SecurityContext> lstSecurityContext = sdEvent.getSecurityContexts();
            for (SecurityContext securityContext : lstSecurityContext) {
                System.out.println("+++++++++++++++++++++++++ Security Context: " + securityContext.getAuthentication().getName());
            }
        }

        //System.out.println("This is a test. ");
		
	}

}
