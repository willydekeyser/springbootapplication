package willydekeyser.customproperties;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReadCustomProperties {

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
}
