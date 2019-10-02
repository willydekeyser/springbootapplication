package willydekeyser.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import willydekeyser.model.Lidgeld;
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
	
	private String to = "";
	private String subject = "";
	private List<Leden> ledenlijst = new ArrayList<Leden>();
	
	@PostMapping("/agendaVersturen")
	public @ResponseBody String verstuurAgenda(@Validated Agenda agenda) {
		ledenlijst = ledenService.getAllLedenNamenlijst(agenda.getSoortenLeden());
		subject = "Agenda voor " + agenda.getDatum_vergadering() + ".";
		try {
			senderService.setMailTeller(0);
			senderService.sendAgendaHTMLMail(new Mail(to, subject, agenda.getFreak(), agenda.getFreaktobe(), 
					agenda.getFreaklesgever(), agenda.getFreaktobelesgever(), 
					agenda.getInfo(), agenda.getDatum_vergadering(), agenda.getDatum_verzenden(), null), ledenlijst);
		} catch (MessagingException | InterruptedException e) {
			System.out.println("Fout: " + e.getMessage());
			return "{\"return\" : \"FOUT\"}";
		}
		return "{\"return\" : \"OK\"}";
	}
	
	@PostMapping("/lidgeldMail")
	public @ResponseBody String verstuurLidgeldMailAgenda(@Validated Lidgeld lidgeld) {
		Leden lid = ledenService.getLedenById(lidgeld.getLeden().getLedenlijst_id());
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		Integer nextYear = year + 1;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd-MMMM-yyyy HH:mm:ss");
		String datumVerzenden = formatter.format(calendar.getTime());
		subject = "Lidgeld " + year + " - " + nextYear;
		try {
			senderService.setMailTeller(0);
			senderService.sendLidgeldHTMLMail(new Mail(to, subject, null, null, null, null, 
					null, null, datumVerzenden, null), lid);
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
