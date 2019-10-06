package willydekeyser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import willydekeyser.json.BackUpService;
import willydekeyser.model.Leden;
import willydekeyser.service.impl.LedenService;

@Controller
@RequestMapping("rapporten")
public class BackUpController {

	@Autowired
	LedenService ledenService;
	
	@Autowired
	BackUpService backUpService;
	
	@GetMapping("/backupleden")
	@ResponseBody
	public String LedenBackupWrite() {
		List<Leden> ledenlijst = ledenService.getAllLeden();
		backUpService.writeLedenBackUp(ledenlijst);
		return "BackUp leden";
	}
	
	@GetMapping("/backupledenread")
	@ResponseBody
	public List<Leden> LedenBackupRead() {
		List<Leden> ledenlijst = backUpService.readLedenBackUp();
		return ledenlijst;
	}
}
