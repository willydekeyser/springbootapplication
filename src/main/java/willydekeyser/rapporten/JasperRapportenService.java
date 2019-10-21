package willydekeyser.rapporten;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class JasperRapportenService {

	public void JasperRapporten(HttpServletResponse response, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource, String jasperReportsFile) {
		try {
			File file = new File(jasperReportsFile);
			JasperDesign jasperDesign = JRXmlLoader.load(file);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			
			JRPdfExporter pdfExporter = new JRPdfExporter();
			pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
			pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
			pdfExporter.exportReport();
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Length", String.valueOf(pdfReportStream.size()));
			response.addHeader("content-disposition", "inline; filename=jasper.pdf");
			
			OutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(pdfReportStream.toByteArray());
			responseOutputStream.close();
			pdfReportStream.close();
			
		} catch (JRException | IOException e) {
			System.err.println("FOUT: " + e.getMessage());
		}
	}
	
	public List<String> Woensdagen(LocalDate datum) {
		List<String> parameters = new ArrayList<String>();
		String maand = datum.getMonth().getDisplayName(TextStyle.FULL, new Locale("nl", "BE"));
		Integer jaar = datum.getYear();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
		LocalDate eersteWoensdag = datum.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY));
		parameters.add(maand + " " + jaar);
		parameters.add(eersteWoensdag.format(formatter));
		for (int i = 1; i < 5; i++) {
			if (eersteWoensdag.plus(Period.ofWeeks(i)).getMonthValue() == datum.getMonthValue()) {
				parameters.add(eersteWoensdag.plus(Period.ofWeeks(i)).format(formatter));
			} else {
				parameters.add("");
			}
		}
		return parameters;
	}
}
