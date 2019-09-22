package willydekeyser.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import willydekeyser.loggers.FileLoggers;

@Component
public class ComputerclubLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Autowired
	FileLoggers fileLogger;
	
    public ComputerclubLogoutSuccessHandler() {
		super();
	}

	@Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
  
        final String refererUrl = request.getHeader("Referer");
        System.out.println("Logout: " + refererUrl + " - " + authentication.getName() + " - " + authentication.getAuthorities().toString());
 
        try {
			fileLogger.schrijfDataToFile("LOGOUT: " + authentication.getName() + " - " + authentication.getAuthorities().toString());
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
        
        super.onLogoutSuccess(request, response, authentication);
    }
}
