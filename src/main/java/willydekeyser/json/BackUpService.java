package willydekeyser.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import willydekeyser.model.Leden;
import willydekeyser.service.impl.LedenService;

@Service
public class BackUpService {
	
	@Autowired
	LedenService ledenService;
	
	public BackUpService() {
		System.out.println("BackUpService: ");
	}

	public List<Leden> readLedenBackUp() {	
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
		try {
			File file = new ClassPathResource("ledenlijst.json").getFile();
			return objectMapper.readValue(file, new TypeReference<List<Leden>>() {
			});
		} catch (IOException e) {
			System.out.println("Unable to read Ledenlijst: " + e.getMessage());
			return null;
		}
	}
	
	public void writeLedenBackUp(List<Leden> ledenlijst) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
		try {	
			File file = new ClassPathResource("ledenlijst.json").getFile();
			objectMapper.writeValue(file, ledenlijst);
		} catch (IOException e) {
			System.out.println("Unable to backup Ledenlijst: " + e.getMessage());
		}
	}
	
}
