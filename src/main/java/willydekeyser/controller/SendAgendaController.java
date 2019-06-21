package willydekeyser.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import willydekeyser.model.Agenda;
import willydekeyser.model.SoortLeden;

@Controller
@RequestMapping("agenda")
public class SendAgendaController {
	
	private List<SoortLeden> selectSoorten;
	private SoortLeden soort = new SoortLeden();
	
	@PostConstruct
	public void init() {
		selectSoorten = soort.SoortLedenLijst();
	}
	
	@GetMapping("/agenda")
	public ModelAndView agenda(Model model) {
		System.out.println("Agenda - Agenda");
		ModelAndView modelandview = new ModelAndView();
		selectSoorten = soort.SoortLedenLijst(1);
		modelandview.addObject("soorten", selectSoorten);
		modelandview.setViewName("agenda/agenda :: agenda_form");
		return modelandview;
	}
	
	@PostMapping("/post")
	public @ResponseBody String post(@Validated Agenda agenda) {
		System.out.println("Agenda: " + agenda);
		return "{\"return\" : \"OK\"}";
	}
	
}
