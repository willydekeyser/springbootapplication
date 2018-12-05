package willydekeyser.model;

import javax.validation.constraints.Size;

public class Agenda {

	private String freak;
	@Size(min=2, max=40, message="Les invullen!")
	private String freaktobe;
	@Size(min=2, max=40, message="Lesgever invullen!")
	private String freaklesgever;
	private String freaktobelesgever;
	private String info;
	
	public Agenda() {
		
	}

	public Agenda(String freak, String freaktobe, String freaklesgever, String freaktobelesgever, String info) {
		this.freak = freak;
		this.freaktobe = freaktobe;
		this.freaklesgever = freaklesgever;
		this.freaktobelesgever = freaktobelesgever;
		this.info = info;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((freak == null) ? 0 : freak.hashCode());
		result = prime * result + ((freaklesgever == null) ? 0 : freaklesgever.hashCode());
		result = prime * result + ((freaktobe == null) ? 0 : freaktobe.hashCode());
		result = prime * result + ((freaktobelesgever == null) ? 0 : freaktobelesgever.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (freak == null) {
			if (other.freak != null)
				return false;
		} else if (!freak.equals(other.freak))
			return false;
		if (freaklesgever == null) {
			if (other.freaklesgever != null)
				return false;
		} else if (!freaklesgever.equals(other.freaklesgever))
			return false;
		if (freaktobe == null) {
			if (other.freaktobe != null)
				return false;
		} else if (!freaktobe.equals(other.freaktobe))
			return false;
		if (freaktobelesgever == null) {
			if (other.freaktobelesgever != null)
				return false;
		} else if (!freaktobelesgever.equals(other.freaktobelesgever))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agenda [freak=" + freak + ", freaktobe=" + freaktobe + ", freaklesgever=" + freaklesgever
				+ ", freaktobelesgever=" + freaktobelesgever + ", info=" + info + "]";
	}
	
	
	
}
