package willydekeyser.component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggedUser implements HttpSessionBindingListener {

	private String username; 
	
	@Autowired
	ServletContext context;
	
	public LoggedUser(ServletContext context) {
        //this.username = username;
		System.out.println("LoggedUser: " + context.getServerInfo());
    }
	
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("valueBound "+ event.getName() + " - " + username);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("valueUnbound "+ event.getName() + " - " + username);
	}

}
