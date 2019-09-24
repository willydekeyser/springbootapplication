package willydekeyser.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import willydekeyser.loggers.FileLoggers;

@Component
public class LoginAttemptsLogger {
	
	@Autowired
	FileLoggers fileLogger;
	
	@EventListener
    public void auditEventHappened(AuditApplicationEvent auditApplicationEvent) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent(); 
        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
        String data = "Principal: " + auditEvent.getPrincipal() + " - " + auditEvent.getType() +
        		"\n		Remote IP address: " + details.getRemoteAddress() +
        		"\n		Request URL: " + auditEvent.getData().get("requestUrl") +
        		"\n		Session Id: " + details.getSessionId()+ 
        		"\n		Datum: " + dateFormat.format(new Date()) + "\n\n";
        try {
			fileLogger.schrijfDataToFile(data);
		} catch (IOException e) {
			System.err.println("Fout: " + e.getMessage());
		}
    }
}
