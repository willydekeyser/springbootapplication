package willydekeyser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KasboekJaartal {
	
	private Integer id;
    private Integer jaartal;
    private List<Rubriek> rubriek = new ArrayList<>();
    
}
