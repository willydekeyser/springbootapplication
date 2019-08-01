package willydekeyser.service;

import java.math.BigDecimal;
import java.util.List;

import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;

public interface IKasboekService {

	List<Kasboek> getAllKasboek();
	List<Kasboek> getAllKasboekRubriek();
	List<Kasboek> getAllKasboekRubriekJaarRubriek(Integer jaartal, Integer rubriekId);
	List<Kasboek> getAllKasboekbyPage(Integer limit, Integer offset);
	List<Kasboek> getAllKasboekRubriekbyPage(Integer limit, Integer offset);
	List<Kasboek> getAllKasboekRubriekJaarRubriekbyPage(Integer jaartal, Integer rubriekId, Integer limit, Integer offset);
	List<KasboekJaartal> getKasboekJaarRubriek();
	BigDecimal[] getSom(Integer jaar, Integer rubriekId);
	List<Integer> getJaartal();
	Kasboek getKasboekById(int id);
	Kasboek addKasboek(Kasboek kasboek);
	Kasboek updateKasboek(Kasboek kasboek);
    void deleteKasboek(int id);
    Boolean kasboekExists(int id);
    Integer countKasboek(Integer jaartal, Integer rubriekId);
        
}
