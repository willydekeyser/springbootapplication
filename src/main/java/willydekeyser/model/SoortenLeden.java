package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SoortenLeden {

	private Integer id;
    private String soortenleden;
    private List<Leden> leden = new ArrayList<>();
    
    public SoortenLeden() {
		
	}
    
    public SoortenLeden(Integer id, String soortenleden) {
		this.id = id;
		this.soortenleden = soortenleden;
	}
    
	public SoortenLeden(Integer id, String soortenleden, List<Leden> leden) {
	
		this.id = id;
		this.soortenleden = soortenleden;
		this.leden = leden;
	}
    
}
