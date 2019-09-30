package willydekeyser.rapporten;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import willydekeyser.model.Lidgeld;

@Service
public class LidgeldBriefService {

	private static final String logo_path = "/image/logo.gif";
    private final String invoice_template = "/reports/ledenlijst.jrxml";
    
	public void generateLidgeldBrief(Lidgeld lidgeld, Locale locale) throws IOException {
		File lidgeldbief = File.createTempFile("Lidgeld_Brief", ".pdf");
		try(FileOutputStream pos = new FileOutputStream(lidgeldbief))
        {
            final JasperReport report = loadTemplate();
            final Map<String, Object> parameters = parameters(lidgeld, locale);
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
        }
        catch (final Exception error) {
            System.out.println("An error occured during PDF creation: " +  error.getMessage());
        }
	}
	
	private Map<String, Object> parameters(Lidgeld lidgeld, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("order",  lidgeld);
        parameters.put("REPORT_LOCALE", locale);
        return parameters;
    }
	
    private JasperReport loadTemplate() throws JRException {

        System.out.println("Invoice template path : " + invoice_template);

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }
}
