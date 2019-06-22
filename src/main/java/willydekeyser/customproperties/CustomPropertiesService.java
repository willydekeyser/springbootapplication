package willydekeyser.customproperties;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomPropertiesService {

	public CustomProperties readCustomProperties() {	
        ObjectMapper objectMapper = new ObjectMapper();
		try {
			File file = new ClassPathResource("customProperties.json").getFile();
			return objectMapper.readValue(file, CustomProperties.class);
		} catch (IOException e) {
			System.out.println("Unable to read properties: " + e.getMessage());
			return null;
		}
	}
	
	public void writeCustomProperties(CustomProperties customPropertie) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {	
			File file = new ClassPathResource("customProperties.json").getFile();
			objectMapper.writeValue(file, customPropertie);
		} catch (IOException e) {
			System.out.println("Unable to write properties: " + e.getMessage());
		}
	}
}
