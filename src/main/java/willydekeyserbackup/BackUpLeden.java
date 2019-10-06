package willydekeyserbackup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import willydekeyser.service.impl.LedenService;

@Service
public class BackUpLeden {

	@Autowired
	LedenService ledenservice;

	public void maakLedenBackUp() {
		
	}
}
