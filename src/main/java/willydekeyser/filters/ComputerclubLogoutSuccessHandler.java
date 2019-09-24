package willydekeyser.filters;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = "Logout:" + 
        "\n		Session Id: " + request.getSession().getId() + 
        "\n		URL: " + refererUrl + 
        "\n		Usernaam: " + authentication.getName() + 
        "\n		Roles: " + authentication.getAuthorities().toString() + 
        "\n		Datum: " + dateFormat.format(new Date()) + "\n\n";
        try {
			fileLogger.schrijfDataToFile(data);
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
        
        super.onLogoutSuccess(request, response, authentication);
    }
}
