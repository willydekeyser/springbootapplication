package willydekeyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.model.Agenda;

@Controller
@RequestMapping("agenda")
public class SendAgendaController {
	
	@GetMapping("/agenda")
	public String agenda(Model model) {
		System.out.println("Agenda - Agenda");
		return "agenda/agenda :: agenda_form";
	}
	
	@PostMapping("/post")
	public @ResponseBody String post(@Validated Agenda agenda) {
		System.out.println("Agenda: " + agenda);
		return "{\"return\" : \"OK\"}";
	}
	
}
