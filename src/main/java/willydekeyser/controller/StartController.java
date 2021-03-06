package willydekeyser.controller;

import static willydekeyser.controller.NamenLijst.COMPUTERCLUB;
import static willydekeyser.controller.NamenLijst.DASHBOARD;
import static willydekeyser.controller.NamenLijst.MIJN_NAAM;
import static willydekeyser.controller.NamenLijst.NAAM;
import static willydekeyser.controller.NamenLijst.PAGINA_TITEL;
import static willydekeyser.controller.NamenLijst.ROLES;
import static willydekeyser.controller.NamenLijst.SERVER_TIME;
import static willydekeyser.controller.NamenLijst.TEST;
import static willydekeyser.controller.NamenLijst.USER;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartController {

	@PostConstruct
	public void init() {
		
	}
	
	/**
	 * 
	 * Start scherm /
	 * 
	 */
	@GetMapping(value = "/")
	public ModelAndView leden() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(PAGINA_TITEL, COMPUTERCLUB);
		modelandview.addObject(NAAM, COMPUTERCLUB);
		modelandview.setViewName("start/index");
		return modelandview;	
	}
	
	@GetMapping("/error")
	public @ResponseBody String error() {
		return "ERROR!";	
	}
	
	@GetMapping("/timeout")
	public @ResponseBody void timeOut() {
		System.out.println("++++++++++++++++++++++ TIME OUT: +++++++++++++++++++++++++++++");
		//return "TIMEOUT!";	
	}
	
	@GetMapping("/pagehide")
	public @ResponseBody void pageHide() {
		System.out.println("++++++++++++++++++++++ Page Hide Event: +++++++++++++++++++++++++++++");
		//return "PAGEHIDE!";	
	}
	
	@GetMapping("/beforeunload")
	public @ResponseBody void beforeUnLoad() {
		System.out.println("++++++++++++++++++++++ Before Unload Event: ++++++++++++++++++++++++++");
		//return "BEFOREUNLOAD!";	
	}
	
	@GetMapping(value = "/start_main")
	public ModelAndView stat_main() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(SERVER_TIME, LocalDateTime.now());
		modelandview.addObject(USER, auth.getName());
		modelandview.addObject(ROLES, auth.getAuthorities());
		modelandview.setViewName("start/start :: start_main");
		return modelandview;	
	}
	
	@GetMapping(value = "/start_menu_logo")
	public String stat_menu_logo(Model model) {
		return "fragments/header :: header_logo";	
	}
	
	@GetMapping(value = "/start_menu_menu")
	public ModelAndView stat_menu_menu() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(USER, auth.getName());
		modelandview.addObject(ROLES, auth.getAuthorities());
		modelandview.setViewName("fragments/header :: header_menu");
		return modelandview;	
	}
	
	@GetMapping(value = "/test")
	public ModelAndView test() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(PAGINA_TITEL, COMPUTERCLUB);
		modelandview.addObject(NAAM, MIJN_NAAM);
		modelandview.addObject(SERVER_TIME, LocalDateTime.now());
		modelandview.setViewName(TEST);
		return modelandview;	
	}
	
	@GetMapping(value = "/dashboard")
	public ModelAndView dashboard() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(PAGINA_TITEL, COMPUTERCLUB);
		modelandview.addObject(NAAM, MIJN_NAAM);
		modelandview.addObject(SERVER_TIME, LocalDateTime.now());
		modelandview.setViewName(DASHBOARD);
		return modelandview;	
	}
	
	@GetMapping(value = "/login")
	public ModelAndView login() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("start/login");
		return modelandview;
	}
	
	@GetMapping(value = "/login_main")
	public ModelAndView login_main() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("start/login :: login");
		return modelandview;
	}
	
	@GetMapping(value = "/testen")
	public ModelAndView testen() {
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject(PAGINA_TITEL, COMPUTERCLUB);
		modelandview.setViewName("/testen");
		return modelandview;	
	}
		
/**
 * 
 * 
 * Rest Controllers
 *
 * 
 */	
	@Secured("ROLE_GOLD")
	@GetMapping("/restcontroller")
	public @ResponseBody String index() {	
		String index = "<p><br/></p>"
				+ "<h1 align='center'>Computerclub Format C</h1><br/><br/>"
				+ "<center><a href='http://localhost:5000'>Home</a><br/> "
				+ "<a href='/leden/restcontroller/leden' target='_blank'>Leden</a> - "
				+ "<a href='/leden/restcontroller/ledenlijst' target='_blank'>Leden by Id</a> - "
				+ "<a href='/leden/restcontroller/soortenleden' target='_blank'>Leden/Soorten leden</a> - "
				+ "<a href='/leden/restcontroller/lidgeld' target='_blank'>Leden/Lidgeld</a><br/>"
				+ "<a href='/lidgeld/restcontroller/lidgeld' target='_blank'>Lidgeld</a> - "
				+ "<a href='/lidgeld/restcontroller/lidgeld/leden' target='_blank'>Lidgeld/Leden</a><br/>"
				+ "<a href='/kasboek/restcontroller/kasboek' target='_blank'>Kasboek</a> - "
				+ "<a href='/kasboek/restcontroller/kasboek/rubriek' target='_blank'>Kasboek/Rubriek</a> - "
				+ "<a href='/kasboek/restcontroller/kasboek/jaarrubriek' target='_blank'>Kasboek/Jaar/Rubriek</a><br/>"
				+ "<a href='/soortenleden/restcontroller/soortenleden' target='_blank'>Soorten leden</a> - "
				+ "<a href='/soortenleden/restcontroller/soortenleden/leden' target='_blank'>Soorten leden/Leden</a><br/>"
				+ "<a href='/rubriek/restcontroller/rubriek' target='_blank'>Rubriek</a> - "
				+ "<a href='/rubriek/restcontroller/rubriek/kasboek' target='_blank'>Rubriek/Kasboek</a> - "
				+ "<a href='/rubriek/restcontroller/rubriek/kasboek/response' target='_blank'>Rubriek - Response</a> - "
				+ "<a href='/mail/index' target='_blank'>Send E-mail</a><br/>"
				+ "<a href='/restcontroller/customproperties' target='_blank'>Custom properties</a> - "
				+ "<a href='/restcontroller/custompropertiesread' target='_blank'>Custom properties read</a> - "
				+ "<a href='/restcontroller/custompropertieswrite' target='_blank'>Custom properties write</a><br/>"
				+ "<a href='/rapporten/lidgeldBrief/4' target='_blank'>Lidgeldbrief lid 4</a><br/>"
				+ "<a href='/rapporten/rubrieken' target='_blank'>Rubrieken</a><br/>"
				+ "<a href='/rapporten/soortenleden' target='_blank'>Soorten leden</a><br/>"
				+ "<a href='/rapporten/ledenlidgeld' target='_blank'>Leden lidgeld lijst</a><br/>"
				+ "<a href='/rapporten/leden/1' target='_blank'>Ledenlijst: Leden</a><br/>"
				+ "<a href='/rapporten/leden/2' target='_blank'>Ledenlijst: Werkendeleden</a><br/>"
				+ "<a href='/rapporten/leden/3' target='_blank'>Ledenlijst: Bestuursleden</a><br/>"
				+ "<a href='/rapporten/leden/4' target='_blank'>Ledenlijst: Oud Leden</a><br/>"
				+ "<a href='/rapporten/leden/5' target='_blank'>Ledenlijst: Iedereen</a><br/>"
				+ "<a href='/rapporten/controleblad' target='_blank'>Controleblad</a><br/>"
				+ "</center>";
		return index;
		
	}	
}
