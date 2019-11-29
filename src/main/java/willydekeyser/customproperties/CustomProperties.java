package willydekeyser.customproperties;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(value = "singleton")
public class CustomProperties {

	private static Integer pauzeAgenda;
	private static BigDecimal lidgeldBedrag;
	private static String test;
	private static Integer een;
	private static Boolean isTrue;
	private static long[] nummers;
	private static List<Adressen> adressen;
	private static Date datum;
	
	public CustomProperties() {
		log.info("CustomProperties in Constructor: ");
	}
	
	@PostConstruct
	public void init() {
		log.info("CustomProperties in PostConstruct: ");
	}
	
	public Integer getPauzeAgenda() {
		return pauzeAgenda;
	}
	
	public void setPauzeAgenda(Integer pauzeAgenda) {
		CustomProperties.pauzeAgenda = pauzeAgenda;
	}
	
	public BigDecimal getLidgeldBedrag() {
		return lidgeldBedrag;
	}
	
	public void setLidgeldBedrag(BigDecimal lidgeldBedrag) {
		CustomProperties.lidgeldBedrag = lidgeldBedrag;
	}
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		CustomProperties.test = test;
	}
	
	public Integer getEen() {
		return een;
	}
	
	public void setEen(Integer een) {
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
