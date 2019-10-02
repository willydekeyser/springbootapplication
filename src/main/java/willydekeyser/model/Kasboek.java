package willydekeyser.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kasboek {

	
	private Integer id;
    private Rubriek rubriek;
    private Integer rubriekId;
    @NotNull(message="Jaar invullen")
    private Integer jaartal;
    private String omschrijving;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;
    private BigDecimal uitgaven;
    private BigDecimal inkomsten;
        
}
