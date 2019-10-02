package willydekeyser.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leden {

	private Integer ledenlijst_id;
	@Size(min=2, max=40, message="Voornaam invullen!")
    private String voornaam;
	@Size(min=2, max=40, message="Familienaam invullen!")
    private String familienaam;
    private String straat;
    private String nr;
    private String postnr;
    private String gemeente;
    private String telefoonnummer;
    private String gsmnummer;
    private String emailadres;
    private String webadres;
    @NotNull(message="Datum invullen")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumlidgeld;
    private boolean ontvangmail;
    private boolean mailvlag;
    @NotNull(message="Soort kiezen!")
    private SoortenLeden soortenleden;
    private Integer soortenledenid;
    private List<Lidgeld> lidgelden = new ArrayList<>();
 
    public Leden(Integer id, String voornaam, String familienaam, String straat, String nr, String postnr,
			String gemeente, String telefoonnummer, String gsmnummer, String emailadres, String webadres,
			LocalDate datumlidgeld, Integer soortenledenid, boolean ontvangmail, boolean mailvlag) {
		this.ledenlijst_id = id;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.straat = straat;
		this.nr = nr;
		this.postnr = postnr;
		this.gemeente = gemeente;
		this.telefoonnummer = telefoonnummer;
		this.gsmnummer = gsmnummer;
		this.emailadres = emailadres;
		this.webadres = webadres;
		this.datumlidgeld = datumlidgeld;
		this.ontvangmail = ontvangmail;
		this.mailvlag = mailvlag;
		this.soortenledenid = soortenledenid;
	}

	public Leden(Integer id, String voornaam, String familienaam, String straat, String nr, String postnr,
			String gemeente, String telefoonnummer, String gsmnummer, String emailadres, String webadres,
			LocalDate datumlidgeld, boolean ontvangmail, boolean mailvlag, SoortenLeden soortenleden,
			List<Lidgeld> lidgelden) {
		this.ledenlijst_id = id;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.straat = straat;
		this.nr = nr;
		this.postnr = postnr;
		this.gemeente = gemeente;
		this.telefoonnummer = telefoonnummer;
		this.gsmnummer = gsmnummer;
		this.emailadres = emailadres;
		this.webadres = webadres;
		this.datumlidgeld = datumlidgeld;
		this.ontvangmail = ontvangmail;
		this.mailvlag = mailvlag;
		this.soortenleden = soortenleden;
		this.lidgelden = lidgelden;
	}
    
	
    
}
