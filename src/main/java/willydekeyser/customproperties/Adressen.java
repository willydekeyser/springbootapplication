package willydekeyser.customproperties;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adressen {

	private String straat;
	private String nummer;
	private String plaats;
	private String postcode;
}
