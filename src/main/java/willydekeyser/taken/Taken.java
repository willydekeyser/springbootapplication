package willydekeyser.taken;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import willydekeyserbackup.BackUpLijsten;

@Service
public class Taken {
	
	@Autowired
	BackUpLijsten backupLijsten;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public Taken() {
		System.out.println("Taken constructor: ");
	}

	@Scheduled(cron = "0 * * * * *")
	public void test() {
		System.out.println("Taak uitgevoerd! " + dateFormat.format(new Date()));
		//backupLijsten.maakbackUp();
	}
}
