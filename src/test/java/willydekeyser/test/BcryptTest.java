package willydekeyser.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {

	@Test
	public void bcryptTest() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		//Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
		//SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		String paswoord = encoder.encode("-belen!25-01-1962-");
		System.out.println("Paswoord: " + paswoord);
		
		paswoord = encoder.encode("!formatC09-11-2018?");
		System.out.println("Paswoord: " + paswoord);
		
		paswoord = encoder.encode("gebruiker-123654789?");
		System.out.println("Paswoord: " + paswoord);
	}
}
