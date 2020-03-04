package willydekeyser.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

@Controller
@RequestMapping("rapporten")
public class BackUpController {

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
	
	@GetMapping("/backup")
	@ResponseBody
	public String BackupWrite() {
		System.out.println("Backup all: ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar calendar = new GregorianCalendar();
		List<Leden> ledenlijst = ledenService.getAllLeden();
		backUpServiceLeden.writeBackUp(ledenlijst, "backup/ledenlijst" + sdf.format(calendar.getTime()) + ".json");
		List<SoortenLeden> soortenledenlijst = soortenledenService.getAllSoortenLeden();
		backUpServiceSoortenleden.writeBackUp(soortenledenlijst, "backup/soortenledenlijst" + sdf.format(calendar.getTime()) + ".json");
		List<Rubriek> rubrieklijst = rubriekService.getAllRubriek();
		backUpServiceRubriek.writeBackUp(rubrieklijst, "backup/rubrieklijst" + sdf.format(calendar.getTime()) + ".json");
		List<Lidgeld> lidgeldlijst = lidgeldService.getAllLidgeld();
		backUpServiceLidgeld.writeBackUp(lidgeldlijst, "backup/lidgeldlijst" + sdf.format(calendar.getTime()) + ".json");
		List<Kasboek> kasboeklijst = kasboekService.getAllKasboek();
		backUpServiceKasboek.writeBackUp(kasboeklijst, "backup/kasboeklijst" + sdf.format(calendar.getTime()) + ".json");
		return "BackUp alle lijsten";
	}
	
	@GetMapping("/backupleden")
	@ResponseBody
	public String LedenBackupWrite() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar calendar = new GregorianCalendar();
		List<Leden> lijst = ledenService.getAllLeden();
		backUpServiceLeden.writeBackUp(lijst, "backup/ledenlijst" + sdf.format(calendar.getTime()) + ".json");
		return "BackUp leden";
	}
	
	@GetMapping("/backupsoortenleden")
	@ResponseBody
	public String SoortenledenBackupWrite() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar calendar = new GregorianCalendar();
		List<SoortenLeden> lijst = soortenledenService.getAllSoortenLeden();
		backUpServiceSoortenleden.writeBackUp(lijst, "backup/soortenledenlijst" + sdf.format(calendar.getTime()) + ".json");
		return "BackUp soortenleden";
	}
	
	@GetMapping("/backuprubriek")
	@ResponseBody
	public String RubriekBackupWrite() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar calendar = new GregorianCalendar();
		List<Rubriek> lijst = rubriekService.getAllRubriek();
		backUpServiceRubriek.writeBackUp(lijst, "backup/rubrieklijst" + sdf.format(calendar.getTime()) + ".json");
		return "BackUp rubriek";
	}
	
	@GetMapping("/backuplidgeld")
	@ResponseBody
	public String LidgeldBackupWrite() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar calendar = new GregorianCalendar();
		List<Lidgeld> lijst = lidgeldService.getAllLidgeld();
		backUpServiceLidgeld.writeBackUp(lijst, "backup/lidgeldlijst" + sdf.format(calendar.getTime()) + ".json");
		return "BackUp lidgeld";
	}
	
	@GetMapping("/backupkasboek")
	@ResponseBody
	public String KasboekBackupWrite() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");	
		Calendar calendar = new GregorianCalendar();
		List<Kasboek> lijst = kasboekService.getAllKasboek();
		backUpServiceKasboek.writeBackUp(lijst, "backup/kasboeklijst" + sdf.format(calendar.getTime()) + ".json");
		return "BackUp rubriek";
	}
	
	@GetMapping("/backupledenread")
	@ResponseBody
	public List<Leden> LedenBackupRead() {
		return backUpServiceLeden.readBackUp("backup/ledenlijst.json");
	}
	
	@GetMapping("/backuplidgeldread")
	@ResponseBody
	public List<Lidgeld> LidgeldBackupRead() {
		return backUpServiceLidgeld.readBackUp("backup/ledenlijst.json");
	}
	
	@GetMapping("/backupkasboekread")
	@ResponseBody
	public List<Kasboek> KasboekBackupRead() {
		return backUpServiceKasboek.readBackUp("backup/ledenlijst.json");
	}
	
	@GetMapping("/backuprubriekread")
	@ResponseBody
	public List<Rubriek> RubriekBackupRead() {
		return backUpServiceRubriek.readBackUp("backup/ledenlijst.json");
	}
	
	@GetMapping("/backupsoortenledenread")
	@ResponseBody
	public List<SoortenLeden> SoortenledenBackupRead() {
		return backUpServiceSoortenleden.readBackUp("backup/ledenlijst.json");
	}
}
