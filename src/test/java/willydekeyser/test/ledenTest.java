package willydekeyser.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import willydekeyser.model.Leden;
import willydekeyser.service.ILedenService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ledenTest {

	@Autowired
	private ILedenService ledenservice;
	
	@Test
	public void getLeden() {
		List<Leden> lijst = ledenservice.getAllLeden();
		assertNotNull(lijst);
		//System.out.println("Leden: " + lijst.toString());
		
	}
	
	@Test
	public void getLid() {
		Integer id = 29988;
		Leden leden = ledenservice.getLedenById(id);
		assertNotNull(leden.getId());
		System.out.println("Lid: " + leden.toString());
		
	}
}
