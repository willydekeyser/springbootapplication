package willydekeyser.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {

	@Test
	public void bcryptTest() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		//Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
		//SCryptPasswordEncoder encoder = new SCryptPasswordEncoder();
		String paswoord = encoder.encode("willy de keyser");
		System.out.println("Paswoord: " + paswoord);
		
		paswoord = encoder.encode("willy de keyser");
		System.out.println("Paswoord: " + paswoord);
	}
}
