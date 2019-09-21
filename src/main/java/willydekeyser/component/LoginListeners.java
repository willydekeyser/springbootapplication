package willydekeyser.component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginListeners implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {


	@Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event)
    {
        UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
        System.out.println("LOGIN name: " + user.getUsername() + " - " + user.getAuthorities().toString());
        try {
			schrijfDataToFile("LOGIN name: " + user.getUsername() + " - " + user.getAuthorities().toString());
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
    }
	
	public void schrijfDataToFile(String data) throws IOException {
		String fileName = "Logindata.txt";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.newLine();
		writer.append("Datum: " + dateFormat.format(new Date()));
		writer.newLine();
		writer.append(data);
		writer.newLine();
		writer.close();
	}
}
