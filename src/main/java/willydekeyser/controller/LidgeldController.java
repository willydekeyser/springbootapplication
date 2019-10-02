package willydekeyser.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.model.Lidgeld;
import willydekeyser.service.ILidgeldService;

@Controller
@RequestMapping("/lidgeld")
public class LidgeldController {

	@Autowired
	private ILidgeldService lidgeldservice;

	List<Lidgeld> lidgeld = new ArrayList<>();
	
	@PostConstruct
	public void init() {

	}

	@GetMapping("/lidgeldTabel")
	public String lidgeld(Model model) {	
		lidgeld = lidgeldservice.getAllLidgeldLeden();
		model.addAttribute("lidgeld", lidgeld);
		model.addAttribute("aantal", lidgeld.size());
		model.addAttribute("datum", LocalDate.now().minusYears(1));
		return "lidgeld/lidgeld :: max_lidgeld_tabel_start";
	}
	
    @GetMapping("/lidgeldById")
    public String lidgeldById(@RequestParam(name="id", required=true, defaultValue="1") Integer id, Model model) {
    	Lidgeld lidgeld = lidgeldservice.getLidgeldById(id);
    	model.addAttribute("lidgeld", lidgeld);
        return "lidgeld/lidgeldbyId";
    }
    
    @GetMapping("/maxlidgeld")
	public String MAXlidgeld(@RequestParam(name = "lidgeld", required = false) String name, Model model) {
		return "lidgeld/lidgeld :: max_lidgeld_tabel_start";
	}
    
    @GetMapping("/maxlidgeldknoppen")
	public String MAXlidgeldKnoppen() {
		return "lidgeld/fragmenten/lidgeldknoppen :: max_lidgeld_knoppen";
	}
    
/**
 * 
 * 
 * Rest Controllers
 *
 * 
 */
    
	@GetMapping("/restcontroller/lidgeld")
	public @ResponseBody List<Lidgeld> lidgeld() {	
		return lidgeldservice.getAllLidgeld();
	}
	
	@GetMapping("/restcontroller/lidgeld/{id}")
	public @ResponseBody List<Lidgeld> lidgeldByLidId(@PathVariable Integer id) {	
		return lidgeldservice.getAllLidgeldByLid(id);
	}
	
	@GetMapping("/restcontroller/lidgeld/leden")
	public @ResponseBody List<Lidgeld> lidgeldLeden() {	
		return lidgeldservice.getAllLidgeldLeden();
	}
	
	@GetMapping("/restcontroller/maxlidgeld")
	public @ResponseBody List<Lidgeld> MAXlidgelddata() {
		return lidgeldservice.getMAXLidgeldLeden();
	}
	
	@GetMapping("/restcontroller/all_lidgeld")
	public @ResponseBody List<Lidgeld> lidgelddata() {
		return lidgeld = lidgeldservice.getAllLidgeldLeden();
	}
	
	@PostMapping("/save_newLidgeld")
	public @ResponseBody List<Lidgeld> save_newLidgeld(@Validated  Lidgeld lidgeld) {
		lidgeldservice.addLidgeld(lidgeld);
		return lidgeldservice.getAllLidgeldByLid(lidgeld.getLeden().getLedenlijst_id());
	}
	
	@PutMapping("/save_updateLidgeld")
	public @ResponseBody List<Lidgeld> save_updateLidgeld(@Validated  Lidgeld lidgeld) {
		lidgeldservice.updateLidgeld(lidgeld);
		return lidgeldservice.getAllLidgeldByLid(lidgeld.getLeden().getLedenlijst_id());
	}
	
	@DeleteMapping("/save_deleteLidgeld")
	public @ResponseBody List<Lidgeld> save_deleteLidgeld(@Validated  Lidgeld lidgeld) {
		lidgeldservice.deleteLidgeld(lidgeld.getId());
		return lidgeldservice.getAllLidgeldByLid(lidgeld.getLeden().getLedenlijst_id());
	}
	
}
