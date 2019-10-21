package willydekeyser.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import willydekeyser.customproperties.CustomProperties;
import willydekeyser.model.Leden;
import willydekeyser.rapporten.JasperRapportenService;
import willydekeyser.service.impl.LedenService;
import willydekeyser.service.impl.LidgeldService;
import willydekeyser.service.impl.RubriekService;
import willydekeyser.service.impl.SoortenLedenService;

@Controller
@RequestMapping("rapporten")
public class RapportenController {

	@Autowired
	private CustomProperties customProperties;
	
	@Autowired
	private LedenService ledenService;
	
	@Autowired
	private LidgeldService lidgeldService;
	
	@Autowired
	private SoortenLedenService soortenledenService;
	
	@Autowired
	private RubriekService rubriekService;
	
	@Autowired
	private JasperRapportenService jasperRapportenService;
	
	private List<String> soortleden = new ArrayList<String>();
		
	@GetMapping("/lidgeldBrief/{lidId}")
	public void LidgeldBrief(@PathVariable Integer lidId, HttpServletResponse response) {
		response.setContentType("text/html");
		List<Leden> ledenlijst = new ArrayList<Leden>();
		ledenlijst.add(ledenService.getLedenById(lidId));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ledenlijst);
		final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("nl", "NL"));
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		Integer nextYear = year + 1;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String datumBrief = formatter.format(calendar.getTime());
		String periode = "'Lidgeld " + year + " - " + nextYear + "'";
		BigDecimal lidgeld = customProperties.getLidgeldBedrag();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("Lidnummer", lidId);
		parameters.put("Bedrag", numberFormat.format(lidgeld));
		parameters.put("Periode", periode);
		parameters.put("Datum", datumBrief);
		parameters.put("IMAGE_DIR", "static/image/");
		String file = "reports/lidgeld.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/soortenleden")
	public void SoortenledenRapport(HttpServletResponse response) {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(soortenledenService.getAllSoortenLeden());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("IMAGE_DIR", "static/image/");
		String file = "reports/soortenleden.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/rubrieken")
	public void RubriekenRapport(HttpServletResponse response) {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rubriekService.getAllRubriek());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("IMAGE_DIR", "static/image/");
		String file = "reports/rubrieken.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/ledenlidgeld")
	public void LedenLidgeld(HttpServletResponse response) {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lidgeldService.getMAXLidgeldLeden());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("IMAGE_DIR", "static/image/");
		String file = "reports/ledenlijstlidgeld.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/leden/{soort}")
	public void Leden(@PathVariable Integer soort, HttpServletResponse response) {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ledenService.getAllLedenNamenlijst(soort));
		Map<String, Object> parameters = new HashMap<>();
		soortleden.add("");
		soortleden.add("Leden");
		soortleden.add("Werkende leden");
		soortleden.add("Bestuurs leden");
		soortleden.add("Oud Leden");
		soortleden.add("Iedereen");
		parameters.put("IMAGE_DIR", "static/image/");
		parameters.put("Titel", "Ledenlijst: " + soortleden.get(soort));
		String file = "reports/leden.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/controleblad/{maand}/{jaar}")
	public void Controleblad(@PathVariable Integer maand, @PathVariable Integer jaar, HttpServletResponse response) {
		if (maand < 1) maand = 1;
		if (maand > 12) maand = 12;
		response.setContentType("text/html");
		LocalDate datum = LocalDate.of(jaar, maand, 1);
		//LocalDate datum = LocalDate.now();
		List<String> vergaderingen = jasperRapportenService.Woensdagen(datum);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ledenService.getAllLedenNamenlijst(1));
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("IMAGE_DIR", "static/image/");
		parameters.put("Titel", vergaderingen.get(0));
		parameters.put("Week1", vergaderingen.get(1));
		parameters.put("Week2", vergaderingen.get(2));
		parameters.put("Week3", vergaderingen.get(3));
		parameters.put("Week4", vergaderingen.get(4));
		parameters.put("Week5", vergaderingen.get(5));
		String file = "reports/controleblad.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/controleblad")
	public void ControlebladDefault(HttpServletResponse response) {
		response.setContentType("text/html");
		LocalDate datum = LocalDate.now();
		List<String> vergaderingen = jasperRapportenService.Woensdagen(datum);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ledenService.getAllLedenNamenlijst(1));
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("IMAGE_DIR", "static/image/");
		parameters.put("Titel", vergaderingen.get(0));
		parameters.put("Week1", vergaderingen.get(1));
		parameters.put("Week2", vergaderingen.get(2));
		parameters.put("Week3", vergaderingen.get(3));
		parameters.put("Week4", vergaderingen.get(4));
		parameters.put("Week5", vergaderingen.get(5));
		String file = "reports/controleblad.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
}
