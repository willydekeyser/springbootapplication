package willydekeyser.sendmail.model;

public class Mail {

	private String to;
	private String subject;
	private String freak;
	private String freaktobe;
	private String freaklesgever;
	private String freaktobelesgever;
	private String info;
	private String datum_vergadering;
	private String datum_verzenden;
	
	public Mail() {
		
	}
	
	public Mail(String to, String subject, String freak, String freaktobe, String freaklesgever,
			String freaktobelesgever, String info, String datum_vergadering, String datum_verzenden) {
		this.to = to;
		this.subject = subject;
		this.freak = freak;
		this.freaktobe = freaktobe;
		this.freaklesgever = freaklesgever;
		this.freaktobelesgever = freaktobelesgever;
		this.info = info;
		this.datum_vergadering = datum_vergadering;
		this.datum_verzenden = datum_verzenden;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
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

	@Override
	public String toString() {
		return "Mail [to=" + to + ", subject=" + subject + ", freak=" + freak + ", freaktobe=" + freaktobe
				+ ", freaklesgever=" + freaklesgever + ", freaktobelesgever=" + freaktobelesgever + ", info=" + info
				+ ", datum_vergadering=" + datum_vergadering + ", datum_verzenden=" + datum_verzenden + "]";
	}

	
}
