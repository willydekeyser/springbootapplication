package willydekeyser.dao;

import java.math.BigDecimal;
import java.util.List;

import willydekeyser.model.Kasboek;
import willydekeyser.model.KasboekJaartal;

public interface IKasboekDAO {

	List<Kasboek> getAllKasboek();
	List<Kasboek> getAllKasboekRubriek();
	List<Kasboek> getAllKasboekRubriekJaarRubriek(Integer jaartal, Integer rubriekId);
	List<Kasboek> getAllKasboekbyPage(Integer limit, Integer offset);
	List<Kasboek> getAllKasboekRubriekbyPage(Integer limit, Integer offset);
	List<Kasboek> getAllKasboekRubriekJaarRubriekbyPage(Integer jaartal, Integer rubriekId, Integer limit, Integer offset);
	BigDecimal[] getSom(Integer jaar, Integer rubriekId);
	List<Integer> getJaartal();
	List<KasboekJaartal> getKasboekJaarRubriek();
	Kasboek getKasboekById(int id);
	List<Kasboek> saveKasboek(List<Kasboek> kasboeklijst);
	Kasboek addKasboek(Kasboek kasboek);
	Kasboek updateKasboek(Kasboek kasboek);
    void deleteKasboek(int id);
    Boolean kasboekExists(int id);
    Integer countKasboek(Integer jaartal, Integer rubriekId);
    
}
