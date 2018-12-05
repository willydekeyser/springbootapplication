package willydekeyser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("agenda")
public class SendAgendaController {

	@GetMapping("/agenda")
	public String agenda(Model model) {
		model.addAttribute("agenda", "agenda");
		return "agenda/agenda :: agenda_form";
	}
	
	@RequestMapping(value="/post", method=RequestMethod.POST)
	public @ResponseBody String post(@RequestBody String tekst) {
		return "OK";
	}
	
}
