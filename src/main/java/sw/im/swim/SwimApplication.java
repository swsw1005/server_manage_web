package sw.im.swim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import sw.im.swim.config.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class SwimApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwimApplication.class, args);
	}

}
