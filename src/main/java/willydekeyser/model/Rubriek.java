package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rubriek {

	private Integer id;
    private String rubriek;
    private List<Kasboek> kasboeken = new ArrayList<>();
	
    public Rubriek(Integer id, String rubriek) {
		super();
		this.id = id;
		this.rubriek = rubriek;
	}
    
    
}
