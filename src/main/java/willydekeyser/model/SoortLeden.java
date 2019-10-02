package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoortLeden {
	
	private Integer id;
    private String soortenleden;
    private Boolean actief;
    private List<SoortLeden> soort = new ArrayList<>();
	

	public SoortLeden(Integer id, String soortenleden, Boolean actief) {
		this.id = id;
		this.soortenleden = soortenleden;
		this.actief = actief;
	}
	
	public List<SoortLeden> SoortLedenLijst() {
		soort.add(new SoortLeden(1,"Leden",true));
		soort.add(new SoortLeden(2,"Werkende leden",false));
		soort.add(new SoortLeden(3,"Bestuursleden",false));
		soort.add(new SoortLeden(4,"Oud leden",false));
		soort.add(new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> SoortLedenLijst(Integer id) {
		switch (id) {
		case 1:
			return setLeden(true);
		case 2:
			return setWerkendeLeden(true);
		case 3:
			return setBestuursLeden(true);
		case 4:
			return setOudLeden(true);
		case 5:
			return setIedereen(true);
		default:
			return setLeden(true);
		}
	}

	public List<SoortLeden> setLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", actief));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Oud leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setWerkendeLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",actief));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Oud leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setBestuursLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",actief));
		soort.set(3, new SoortLeden(4,"Oud leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setOudLeden(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Oud leden",actief));
		soort.set(4, new SoortLeden(5,"Iedereen",false));
		return soort;
	}
	
	public List<SoortLeden> setIedereen(Boolean actief) {
		soort.set(0, new SoortLeden(1,"Leden", false));
		soort.set(1, new SoortLeden(2,"Werkende leden",false));
		soort.set(2, new SoortLeden(3,"Bestuursleden",false));
		soort.set(3, new SoortLeden(4,"Oud leden",false));
		soort.set(4, new SoortLeden(5,"Iedereen",actief));
		return soort;
	}
	
}
