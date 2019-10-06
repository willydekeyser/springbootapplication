package willydekeyser.taken;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Taken {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public Taken() {
		System.out.println("Taken constructor: ");
	}

	@Scheduled(cron = "0 */10 * * * *")
	//@Scheduled(fixedRate = 2000)
	public void test() {
		System.out.println("Taak uitgevoerd! " + dateFormat.format(new Date()));
	}
}
