package willydekeyser.taken;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import willydekeyser.backup.BackUpLijsten;

@Service
public class Taken {
	
	@Autowired
	BackUpLijsten backupLijsten;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public Taken() {
		System.out.println("Taken constructor: ");
	}

	@Scheduled(cron = "0 0 * * * 3,6")
	public void test() {
		System.out.println("Taak uitgevoerd! " + dateFormat.format(new Date()));
		//backupLijsten.maakbackUp();
		
		// Voorbeeld van de volgende site
		// https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl
		String inline = "";
		try {
			URL url = new URL("https://www.e-lotto.be/cache/dgResultsWithAddonsForDrawDate/NL/Lotto6-2019.10.26.json");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			System.out.println("Response code is: " +responsecode);
			if(responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " +responsecode);
			else
			{
				Scanner sc = new Scanner(url.openStream());
				while(sc.hasNext())
				{
					inline+=sc.nextLine();
				}
				System.out.println("\nJSON Response in String format"); 
				System.out.println(inline);
				sc.close();
			}
			conn.disconnect();
		} catch (Exception e) {
			System.err.println("Fout: " + e.getMessage());
		}	
					
	}
}
