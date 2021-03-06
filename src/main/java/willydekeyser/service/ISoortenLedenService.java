package willydekeyser.service;

import java.util.List;

import willydekeyser.model.SoortenLeden;

public interface ISoortenLedenService {

	List<SoortenLeden> getAllSoortenLeden();
	List<SoortenLeden> getAllSoortenLedenLeden();
	SoortenLeden getSoortenLedenById(int id);
	List<SoortenLeden> saveSoortenleden(List<SoortenLeden> soortenledenlijst);
    SoortenLeden addSoortenLeden(SoortenLeden soortenLeden);
    SoortenLeden updateSoortenLeden(SoortenLeden soortenLeden);
    void deleteSoortenLeden(int id);
    Boolean soortenLedenExists(int id);
    Boolean ledenExistsBySoortenledenId(int id);
    
}
