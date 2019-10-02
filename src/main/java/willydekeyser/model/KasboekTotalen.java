package willydekeyser.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KasboekTotalen {

    private BigDecimal uitgaven;
    private BigDecimal inkomsten;
    
}
