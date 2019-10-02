package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoortenLeden {

	private Integer id;
    private String soortenleden;
    private List<Leden> leden = new ArrayList<>();
       
    public SoortenLeden(Integer id, String soortenleden) {
		this.id = id;
		this.soortenleden = soortenleden;
	}
    
}
