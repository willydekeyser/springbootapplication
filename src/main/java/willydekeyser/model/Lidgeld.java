package willydekeyser.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lidgeld {

	private Integer id;
    private Leden leden;
    private Integer ledenId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;
    private BigDecimal bedrag;
    
}
