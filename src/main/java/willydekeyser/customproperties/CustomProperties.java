package willydekeyser.customproperties;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;


@Component
@Scope(value = "singleton")
public class CustomProperties {

	private static int pauzeAgenda;
	private static String test;
	private static int een;
	private static Boolean isTrue;
	private static long[] nummers;
	private static List<Adressen> adressen;
	private static Date datum;
	
	public CustomProperties() {
		System.out.println("CustomProperties in Constructor: ");
	}
	
	@PostConstruct
	public void init() {
		System.out.println("CustomProperties in PostConstruct: ");
	}
	
	public int getPauzeAgenda() {
		return pauzeAgenda;
	}
	
	public void setPauzeAgenda(int pauzeAgenda) {
		CustomProperties.pauzeAgenda = pauzeAgenda;
	}
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		CustomProperties.test = test;
	}
	
	public int getEen() {
		return een;
	}
	
	public void setEen(int een) {
		CustomProperties.een = een;
	}
	
	public Boolean getIsTrue() {
		return isTrue;
	}
	
	public void setIsTrue(Boolean isTrue) {
		CustomProperties.isTrue = isTrue;
	}
	
	public long[] getNummers() {
		return nummers;
	}
	
	public void setNummers(long[] nummers) {
		CustomProperties.nummers = nummers;
	}
	
	public List<Adressen> getAdressen() {
		return adressen;
	}
	
	public void setAdressen(List<Adressen> adressen) {
		CustomProperties.adressen = adressen;
	}
	
	public Date getDatum() {
		return datum;
	}
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	public void setDatum(Date datum) {
		CustomProperties.datum = datum;
	}
	
}
