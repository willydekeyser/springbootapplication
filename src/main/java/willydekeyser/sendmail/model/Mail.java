package willydekeyser.sendmail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

	private String to;
	private String subject;
	private String freak;
	private String freaktobe;
	private String freaklesgever;
	private String freaktobelesgever;
	private String info;
	private String datum_vergadering;
	private String datum_verzenden;
	private String lidgeld_periode;
		
	

	
}
