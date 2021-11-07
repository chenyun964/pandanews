package sg.edu.smu.cs203.pandanews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import sg.edu.smu.cs203.pandanews.config.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class PandaNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PandaNewsApplication.class, args);
	}
}
