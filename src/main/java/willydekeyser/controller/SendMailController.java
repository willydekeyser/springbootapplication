package willydekeyser.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.model.Agenda;
import willydekeyser.model.Leden;
import willydekeyser.sendmail.model.Mail;
import willydekeyser.sendmail.service.MailSenderService;
import willydekeyser.service.impl.LedenService;

@Controller
@RequestMapping("mail")
public class SendMailController {
	
	@Autowired
    private MailSenderService senderService;
	
	@Autowired
    private LedenService ledenService;
	
	private String to = "willy.de.keyser@skynet.be";
	private String subject = "";
	private List<Leden> ledenlijst = new ArrayList<Leden>();
	
	
	@PostMapping("/post")
	public @ResponseBody String index(@Validated Agenda agenda) {
		ledenlijst = ledenService.getAllLedenNamenlijst(3);
		subject = "Agenda voor " + agenda.getDatum_vergadering() + ".";
		try {
			senderService.setMailTeller(0);
			senderService.sendHTMLMail(new Mail(to, subject, agenda.getFreak(), agenda.getFreaktobe(), 
					agenda.getFreaklesgever(), agenda.getFreaktobelesgever(), 
					agenda.getInfo(), agenda.getDatum_vergadering(), agenda.getDatum_verzenden()), ledenlijst);
		} catch (MessagingException | InterruptedException e) {
			System.out.println("Fout: " + e.getMessage());
			return "{\"return\" : \"FOUT\"}";
		}
		return "{\"return\" : \"OK\"}";
	}
	
	@GetMapping("/mail")
	public String mail(Model model) {
		model.addAttribute("message", "Computerclub Format C Agenda Versturen..");
		return "/mail/mail";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody String getTime() {
        return "{\"return_progress\" : \"" + senderService.getMailTeller() + "\","
        		+ "\"return_max\" : \"" + senderService.getMaxMailTeller() + "\"}";
    }
	
}
