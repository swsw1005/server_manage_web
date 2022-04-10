package sw.im.swim;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import sw.im.swim.config.PostConstruct;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class SwimApplication {

    public static void main(String[] args) {
        log.info("Application START (1/2)!!!!");
        SpringApplication.run(SwimApplication.class, args);
    }

}
