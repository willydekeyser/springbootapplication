package willydekeyser.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import willydekeyser.rapporten.LidgeldBriefService;
import willydekeyser.service.impl.LedenService;
import willydekeyser.service.impl.LidgeldService;

@Controller
@RequestMapping("rapporten")
public class RapportenController {

	@Autowired
	LidgeldBriefService lidgeldBrief;
	

	@Autowired
	LedenService ledenService;
	
	@Autowired
	LidgeldService lidgeldService;
	
	@GetMapping(value = "/lidgeldRapport")
	public void lidgeldRapport(HttpServletResponse response) throws IOException, JRException {	

		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ledenService.getAllLeden());
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/lidgeld.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
		exporter.exportReport();
	}
}
