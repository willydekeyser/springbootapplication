package willydekeyser.service;

import java.util.List;

import willydekeyser.model.Leden;

public interface ILedenService {

	List<Leden> getAllLeden();
	List<Leden> getAllLedenNamenlijst(Integer soort);
	List<Leden> getLedenNamenlijstSoort(String soort);
	List<Leden> getAllLedenSoortenleden(Integer soort);
	List<Leden> getAllLedenLidgeld();
	Leden getLedenById(Integer id);
	List<Leden> saveLeden(List<Leden> ledenlijst);
    Leden addLeden(Leden leden);
    Leden updateLeden(Leden leden);
    Integer deleteLeden(int id);
    boolean ledenExists(int id);
	
}
