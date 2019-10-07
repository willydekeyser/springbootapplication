package willydekeyserbackup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.json.BackUpService;
import willydekeyser.model.Kasboek;
import willydekeyser.model.Leden;
import willydekeyser.model.Lidgeld;
import willydekeyser.model.Rubriek;
import willydekeyser.model.SoortenLeden;
import willydekeyser.service.impl.KasboekService;
import willydekeyser.service.impl.LedenService;
import willydekeyser.service.impl.LidgeldService;
import willydekeyser.service.impl.RubriekService;
import willydekeyser.service.impl.SoortenLedenService;

@Service
public class BackUpLijsten {

	@Autowired
	LedenService ledenService;
	
	@Autowired
	SoortenLedenService soortenledenService;
	
	@Autowired
	RubriekService rubriekService;
	
	@Autowired
	LidgeldService lidgeldService;
	
	@Autowired
	KasboekService kasboekService;
	
	@Autowired
	BackUpService<Leden> backUpServiceLeden;
	
	@Autowired
	BackUpService<SoortenLeden> backUpServiceSoortenleden;
	
	@Autowired
	BackUpService<Rubriek> backUpServiceRubriek;
	
	@Autowired
	BackUpService<Lidgeld> backUpServiceLidgeld;
	
	@Autowired
	BackUpService<Kasboek> backUpServiceKasboek;

	public void maakbackUp() {
		List<Leden> ledenlijst = ledenService.getAllLeden();
		backUpServiceLeden.writeBackUp(ledenlijst, "ledenlijst.json");
		List<SoortenLeden> soortenledenlijst = soortenledenService.getAllSoortenLeden();
		backUpServiceSoortenleden.writeBackUp(soortenledenlijst, "soortenledenlijst.json");
		List<Rubriek> rubrieklijst = rubriekService.getAllRubriek();
		backUpServiceRubriek.writeBackUp(rubrieklijst, "rubrieklijst.json");
		List<Lidgeld> lidgeldlijst = lidgeldService.getAllLidgeld();
		backUpServiceLidgeld.writeBackUp(lidgeldlijst, "lidgeldlijst.json");
		List<Kasboek> kasboeklijst = kasboekService.getAllKasboek();
		backUpServiceKasboek.writeBackUp(kasboeklijst, "kasboeklijst.json");
		System.out.println("Backup van alle lijsten!");
	}
	public void maakLedenBackUp() {
		List<Leden> lijst = ledenService.getAllLeden();
		backUpServiceLeden.writeBackUp(lijst, "ledenlijst.json");
	}
	
	public void maakLidgeldBackUp() {
		List<Lidgeld> lijst = lidgeldService.getAllLidgeld();
		backUpServiceLidgeld.writeBackUp(lijst, "lidgeldlijst.json");
	}
	
	public void maakKasboekBackUp() {
		List<Kasboek> lijst = kasboekService.getAllKasboek();
		backUpServiceKasboek.writeBackUp(lijst, "kasboeklijst.json");
	}
	
	public void maakRubriekBackUp() {
		List<Rubriek> lijst = rubriekService.getAllRubriek();
		backUpServiceRubriek.writeBackUp(lijst, "rubrieklijst.json");
	}
	
	public void maakSoortenledenBackUp() {
		List<SoortenLeden> lijst = soortenledenService.getAllSoortenLeden();
		backUpServiceSoortenleden.writeBackUp(lijst, "soortenledenlijst.json");
	}

}
