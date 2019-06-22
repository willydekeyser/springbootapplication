package willydekeyser.customproperties;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomProperties {

	private int pauzeAgenda;
	private String test;
	private int een;
	private Boolean isTrue;
	private long[] nummers;
	private List<Adressen> adressen;
		
}
