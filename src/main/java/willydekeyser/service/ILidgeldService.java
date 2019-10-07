package willydekeyser.service;

import java.util.List;

import willydekeyser.model.Lidgeld;

public interface ILidgeldService {

	List<Lidgeld> getAllLidgeld();
	List<Lidgeld> getAllLidgeldLeden();
	List<Lidgeld> getAllLidgeldByLid(int id);
	List<Lidgeld> getMAXLidgeldLeden();
	Lidgeld getLidgeldById(int id);
	List<Lidgeld> saveLidgeld(List<Lidgeld> lidgeldlijst);
	Lidgeld addLidgeld(Lidgeld lidgeld);
	Lidgeld updateLidgeld(Lidgeld lidgeld);
    Integer deleteLidgeld(int id);
    boolean lidgeldExists(int id);
    
}
