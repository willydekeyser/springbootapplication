package willydekeyser.model;

import javax.validation.constraints.Size;

public class Agenda {

	private String freak;
	@Size(min=2, max=40, message="Les invullen!")
	private String freaktobe;
	private String freaklesgever;
	@Size(min=2, max=40, message="Lesgever invullen!")
	private String freaktobelesgever;
	private String info;
	private String datum_vergadering;
	private String datum_verzenden;
	private Integer soortenLeden;
	
	public Agenda() {
		
	}

	public Agenda(String freak, String freaktobe, String freaklesgever, String freaktobelesgever, String info, String datum_vergadering, String datum_verzenden, Integer soortenLeden) {
		this.freak = freak;
		this.freaktobe = freaktobe;
		this.freaklesgever = freaklesgever;
		this.freaktobelesgever = freaktobelesgever;
		this.info = info;
		this.datum_vergadering = datum_vergadering;
		this.datum_verzenden = datum_verzenden;
		this.soortenLeden = soortenLeden;
	}

	public String getFreak() {
		return freak;
	}

	public void setFreak(String freak) {
		this.freak = freak;
	}

	public String getFreaktobe() {
		return freaktobe;
	}

	public void setFreaktobe(String freaktobe) {
		this.freaktobe = freaktobe;
	}

	public String getFreaklesgever() {
		return freaklesgever;
	}

	public void setFreaklesgever(String freaklesgever) {
		this.freaklesgever = freaklesgever;
	}

	public String getFreaktobelesgever() {
		return freaktobelesgever;
	}

	public void setFreaktobelesgever(String freaktobelesgever) {
		this.freaktobelesgever = freaktobelesgever;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDatum_vergadering() {
		return datum_vergadering;
	}

	public void setDatum_vergadering(String datum_vergadering) {
		this.datum_vergadering = datum_vergadering;
	}

	public String getDatum_verzenden() {
		return datum_verzenden;
	}

	public void setDatum_verzenden(String datum_verzenden) {
		this.datum_verzenden = datum_verzenden;
	}

	public Integer getSoortenLeden() {
		return soortenLeden;
	}

	public void setSoortenLeden(Integer soortenLeden) {
		this.soortenLeden = soortenLeden;
	}

	@Override
	public String toString() {
		return "Agenda [freak=" + freak + ", freaktobe=" + freaktobe + ", freaklesgever=" + freaklesgever
				+ ", freaktobelesgever=" + freaktobelesgever + ", info=" + info + ", datum_vergadering="
				+ datum_vergadering + ", datum_verzenden=" + datum_verzenden + ", soortenLeden=" + soortenLeden + "]";
	}

}
