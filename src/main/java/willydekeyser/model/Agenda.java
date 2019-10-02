package willydekeyser.model;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {

	private String freak;
	@Size(min=2, max=40, message="Les invullen!")
	private String freaktobe;
	private String freaklesgever;
	@Size(min=2, max=40, message="Lesgever invullen!")
	private String freaktobelesgever;
	private String info;
	private String datum_vergadering;
	private String datum_verzenden;
	private Integer soortenLeden;
	
}
