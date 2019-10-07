package willydekeyser.dao;

import java.util.List;

import willydekeyser.model.Leden;

public interface ILedenDAO {

	List<Leden> getAllLeden();
	List<Leden> getAllLedenNamenlijst(Integer soort);
	List<Leden> getAllLedenSoortenleden(Integer soort);
	List<Leden> getAllLedenLidgeld();
	Leden getLedenById(int id);
	List<Leden> saveLeden(List<Leden> ledenlijst);
    Leden addLeden(Leden leden);
    Leden updateLeden(Leden leden);
    Integer deleteLeden(int id);
    boolean ledenExists(int id);
    
}
