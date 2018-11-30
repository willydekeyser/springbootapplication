package willydekeyser;

import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan
@SpringBootApplication
@EnableAsync
public class SpringDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDatabaseApplication.class, args);
		
		MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
		try {
			model = reader.read(new FileReader("pom.xml"));
			System.out.println(model.getId());
	        System.out.println(model.getGroupId());
	        System.out.println(model.getArtifactId());
	        System.out.println(model.getVersion());
		} catch (IOException | XmlPullParserException e) {
			System.err.println("ERROR " + e.getMessage());
		}
        
		
		
		
		
		
		
		
		
		
	}
}
