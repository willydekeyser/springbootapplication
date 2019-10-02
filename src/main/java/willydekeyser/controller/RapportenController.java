package willydekeyser.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import willydekeyser.service.impl.SoortenLedenService;

@Controller
@RequestMapping("rapporten")
public class RapportenController {

	@Autowired
	private CustomProperties customProperties;
	
	@Autowired
	private LedenService ledenService;
	
	@Autowired
	private SoortenLedenService soortenledenService;
	
	@Autowired
	private JasperRapportenService jasperRapportenService;
	
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
		String file = "/reports/lidgeld.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
	
	@GetMapping("/soortenleden")
	public void SoortenledenRapport(HttpServletResponse response) {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(soortenledenService.getAllSoortenLeden());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("IMAGE_DIR", "static/image/");
		String file = "/reports/soortenleden.jrxml";
		jasperRapportenService.JasperRapporten(response, parameters, dataSource, file);
	}
}
