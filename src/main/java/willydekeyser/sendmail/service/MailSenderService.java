package willydekeyser.sendmail.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import willydekeyser.customproperties.CustomProperties;
import willydekeyser.loggers.FileLoggers;
import willydekeyser.model.Leden;
import willydekeyser.sendmail.model.Mail;

@Service
@Configurable
public class MailSenderService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;
		
	@Autowired
	private CustomProperties customProperties;
	
	@Autowired
	FileLoggers fileLogger;
	
	private Integer mailTeller = 1;
	private Integer maxMailTeller = 0;
	

	public void setMailTeller(Integer mailTeller) {
		this.mailTeller = mailTeller;
	}
	
	public Integer getMailTeller() {
		return mailTeller;
	}
	
	public void setMaxMailTeller(Integer maxMailTeller) {
		this.maxMailTeller = maxMailTeller;
	}
	
	public Integer getMaxMailTeller() {
		return maxMailTeller;
	}
	
    // Use it to send Simple text emails
	@Async
    public void sendSimpleMail(Mail mail, String tekst) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mail.getTo());
        message.setFrom("cfc.schatbewaarder@cformatc.be");
        message.setReplyTo("contact@cformatc.be");
        message.setSubject(mail.getSubject());
        message.setText(tekst);
        //mailSender.send(message);
    }

    // Use it to send HTML emails
	@Async
    public void sendAgendaHTMLMail(Mail mail, List<Leden> ledenlijst) throws MessagingException, InterruptedException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.addAttachment("logo.gif", new ClassPathResource("static/image/logo.gif"));
        maxMailTeller = ledenlijst.size();
        Context context = new Context();
        Integer index = 0;
        String data = "Planning: " + mail.getDatum_vergadering() + ".\n\n";
        try {
			fileLogger.schrijfAgendaToFile(data);
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
        for(Leden leden : ledenlijst ) {
        	if (leden.getEmailadres() == "" || leden.getEmailadres().isEmpty() || leden.getEmailadres().isBlank()) {
        		continue;
        	}
        	mail.setTo(leden.getEmailadres());
            System.out.println("Zend email... " + leden.getVoornaam() + " " + leden.getFamilienaam() + " " + leden.getEmailadres() + " - " + mail.getTo());
        	context.setVariable("naam", leden.getVoornaam());
        	context.setVariable("titel", "Planning: " + mail.getDatum_vergadering());
        	context.setVariable("freaks", mail.getFreak());
        	context.setVariable("freakslesgever", mail.getFreaklesgever());
        	context.setVariable("freakstobe", mail.getFreaktobe());
        	context.setVariable("freakstobelesgever", mail.getFreaktobelesgever());
        	context.setVariable("info", mail.getInfo());
        	context.setVariable("datum_verzenden", mail.getDatum_verzenden());
            String html = templateEngine.process("mail/agenda", context);
        	setMailTeller(index++);
	        try {
	        	messageHelper.setTo(new InternetAddress(mail.getTo(), leden.getVoornaam() + " " + leden.getFamilienaam()));
				messageHelper.setFrom(new InternetAddress("cfc.schatbewaarder@cformatc.be", "Computerclub Format C"));
				//messageHelper.setCc(new InternetAddress("cfc.schatbewaarder@cformatc.be", "Willy De Keyser"));
		        //messageHelper.setBcc(new InternetAddress("wdkeyser@gmail.com", "Willy De Keyser"));
		        messageHelper.setReplyTo(new InternetAddress("contact@cformatc.be", "Computerclub Format C"));
			} catch (UnsupportedEncodingException e) {
				System.err.println("Zend E-mail error: " + e.getMessage());
				try {
					fileLogger.schrijfAgendaToFile("Zend E-mail error: " + e.getMessage() + " - " + leden.getVoornaam() + " " + leden.getFamilienaam() + " " + leden.getEmailadres());
				} catch (IOException ex) {
					System.err.println("Fout schrijven naar File: " + ex.getMessage());
				}
			}
	        messageHelper.setSubject(mail.getSubject());
	        messageHelper.setText(html, true);
	        
	       	//mailSender.send(message);
	    
	        if(index == 1) {
	        	data = html + "\n\n";
		        try {
					fileLogger.schrijfAgendaToFile(data);
				} catch (IOException e) {
					System.err.println("Fout schrijven naar File: " + e.getMessage());
				}
	        }
	        data = "Agenda " + index + " - " + leden.getVoornaam() + " " + leden.getFamilienaam() + " " + leden.getEmailadres();
	        try {
				fileLogger.schrijfAgendaToFile(data);
			} catch (IOException e) {
				System.err.println("Fout schrijven naar File: " + e.getMessage());
			}
    		Thread.sleep(customProperties.getPauzeAgenda() * 1000);
        }
        setMailTeller(-1);
    }
	
	@Async
    public void sendLidgeldHTMLMail(Mail mail, Leden lid) throws MessagingException, InterruptedException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.addAttachment("logo.gif", new ClassPathResource("static/image/logo.gif"));
        final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));
        Context context = new Context();
        
    	//mail.setTo(lid.getEmailadres());
    	mail.setTo("willy.de.keyser@skynet.be");
    	
        System.out.println("Zend email... " + lid.getVoornaam() + " " + lid.getFamilienaam() + " " + lid.getEmailadres() + " - " + mail.getTo());
    	context.setVariable("voornaam", lid.getVoornaam());
    	context.setVariable("familienaam", lid.getFamilienaam());
    	context.setVariable("straat", lid.getStraat());
    	context.setVariable("nr", lid.getNr());
    	context.setVariable("postnummer", lid.getPostnr());
    	context.setVariable("gemeente", lid.getGemeente());
    	context.setVariable("telefoonnummer", lid.getTelefoonnummer());
    	context.setVariable("gsmnummer", lid.getGsmnummer());
    	context.setVariable("emailadres", lid.getEmailadres());
    	context.setVariable("webadres", lid.getWebadres());
    	context.setVariable("datum_verzenden", mail.getDatum_verzenden());
    	context.setVariable("lidgeldbedrag", numberFormat.format(customProperties.getLidgeldBedrag()));
    	context.setVariable("lidgeldperiode", mail.getSubject());
        String html = templateEngine.process("mail/lidgeld", context);
        try {
        	messageHelper.setTo(new InternetAddress(mail.getTo(), lid.getVoornaam() + " " + lid.getFamilienaam()));
			messageHelper.setFrom(new InternetAddress("cfc.schatbewaarder@cformatc.be", "Computerclub Format C"));
			//messageHelper.setCc(new InternetAddress("cfc.schatbewaarder@cformatc.be", "Willy De Keyser"));
	        //messageHelper.setBcc(new InternetAddress("wdkeyser@gmail.com", "Willy De Keyser"));
	        messageHelper.setReplyTo(new InternetAddress("contact@cformatc.be", "Computerclub Format C"));
		} catch (UnsupportedEncodingException e) {
			System.err.println("Zend E-mail error: " + e.getMessage());
			try {
				fileLogger.schrijfLidgeldToFile("Zend E-mail error: " + e.getMessage() + " - " + lid.getVoornaam() + " " + lid.getFamilienaam() + " " + lid.getEmailadres());
			} catch (IOException ex) {
				System.err.println("Fout schrijven naar File: " + ex.getMessage());
			}
		}
        messageHelper.setSubject(mail.getSubject());
        messageHelper.setText(html, true);
        
       	mailSender.send(message);
       	
       	String data = "Lidgeld " + lid.getVoornaam() + " " + lid.getFamilienaam() + " " + lid.getEmailadres() + "\n\n";
    	data += html + "\n\n";
        try {
			fileLogger.schrijfLidgeldToFile(data);
		} catch (IOException e) {
			System.err.println("Fout schrijven naar File: " + e.getMessage());
		}
		Thread.sleep(customProperties.getPauzeAgenda() * 1000);
        
        setMailTeller(-1);
    }
}
