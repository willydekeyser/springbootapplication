package willydekeyser.customproperties;

import org.springframework.stereotype.Component;

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
public class CustomProperties {

	private int een;
	private String test;
		
}
