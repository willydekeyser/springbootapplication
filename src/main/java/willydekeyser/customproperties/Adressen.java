package willydekeyser.customproperties;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adressen {

	private String straat;
	private String nummer;
	private String plaats;
	private String postcode;
}
