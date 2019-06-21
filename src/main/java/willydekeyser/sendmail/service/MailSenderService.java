package willydekeyser.sendmail.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import willydekeyser.customproperties.ReadCustomProperties;
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
	private ReadCustomProperties readCustomProperties;
	
	@Autowired
	private CustomProperties customProperties;
		
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
        mailSender.send(message);
    }

    // Use it to send HTML emails
	@Async
    public void sendHTMLMail(Mail mail, List<Leden> ledenlijst, Integer pauze) throws MessagingException, InterruptedException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.addAttachment("logo.gif", new ClassPathResource("static/image/logo.gif"));

        maxMailTeller = ledenlijst.size();
        
        Context context = new Context();
        Integer index = 0;
        for(Leden leden : ledenlijst ) {
        	
            System.out.println("Zend email... " + leden.getVoornaam() + " " + leden.getFamilienaam() + " " + leden.getEmailadres() + " - " + mail.getTo());
            customProperties = readCustomProperties.readCustomProperties();
        	System.out.println("PAUZE: " + customProperties.getEen() + " - " + customProperties.getTest());
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
			}
	        messageHelper.setSubject(mail.getSubject());
	        messageHelper.setText(html, true);
	        //mailSender.send(message);
	        
	        System.out.println("Sleeping now... " + Thread.currentThread().getName());
    		Thread.sleep(customProperties.getEen() * 1000);

    		System.out.println("E-mail verzonden: " + index);
    		
        }
        setMailTeller(-1);
        
    }
}
