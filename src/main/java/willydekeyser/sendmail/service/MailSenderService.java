package willydekeyser.sendmail.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.mail.MessagingException;
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

import willydekeyser.model.Leden;
import willydekeyser.sendmail.model.Mail;

@Service
@Configurable
public class MailSenderService {

	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;
	
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
    public void sendSimpleMail(Mail mail) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mail.getTo());
        message.setFrom("wdkeyser@gmail.com");
        message.setSubject(mail.getSubject());
        //message.setText(mail.getContent());

        //mailSender.send(message);
    }

    // Use it to send HTML emails
	@Async
    public void sendHTMLMail(Mail mail, List<Leden> ledenlijst) throws MessagingException, InterruptedException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.addAttachment("logo.gif", new ClassPathResource("static/image/logo.gif"));

        maxMailTeller = ledenlijst.size();
        
        Context context = new Context();
        Integer i = 0;
        for(Leden leden : ledenlijst ) {
        	i = i + 1;
    		System.out.println("Sleeping now... " + Thread.currentThread().getName());
    		
    		for(int j=1; j<10; j++) {
    			Thread.sleep(1000);
    		} 
    		
            System.out.println("Sending email... " + leden.getVoornaam() + " " + leden.getFamilienaam());
        	
        	context.setVariable("naam", leden.getVoornaam());
        	context.setVariable("titel", "Planning: " + mail.getDatum_vergadering());
        	context.setVariable("freaks", mail.getFreak());
        	context.setVariable("freakslesgever", mail.getFreaklesgever());
        	context.setVariable("freakstobe", mail.getFreaktobe());
        	context.setVariable("freakstobelesgever", mail.getFreaktobelesgever());
        	context.setVariable("info", mail.getInfo());
        	context.setVariable("datum_verzenden", mail.getDatum_verzenden());
            String html = templateEngine.process("mail/agenda", context);
        	setMailTeller(i);
	        helper.setTo(mail.getTo());
	        helper.setSubject(mail.getSubject() + " " + i);
	        helper.setText(html, true);
	        mailSender.send(message);
	        System.out.println("Send E-mail: " + i);
	        
        }
        setMailTeller(10000);
        
    }
}
