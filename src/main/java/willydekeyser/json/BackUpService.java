package willydekeyser.json;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import willydekeyser.service.impl.LedenService;

@Service
public class BackUpService<T> {
	
	@Autowired
	LedenService ledenService;
	
	public List<T> readBackUp(String jsonBestand) {	
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
		try {
			File file = new File(jsonBestand);
			return objectMapper.readValue(file, new TypeReference<List<T>>() {
			});
		} catch (IOException e) {
			System.out.println("Lezen van " + jsonBestand + " is mislukt: " + e.getMessage());
			return null;
		}
	}
	
	public void writeBackUp(List<T> lijst, String jsonBestand) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
		try {	
			File file = new File(jsonBestand);
			objectMapper.writeValue(file, lijst);
		} catch (IOException e) {
			System.out.println("BackUp maken naar " + jsonBestand + " mislukt: " + e.getMessage());
		}
	}
	
}
