package sw.im.swim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import kr.swim.util.enc.EncodingException;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class SwimApplication {

    public static void main(String[] args) throws EncodingException, InterruptedException {
        log.info("Application START (1/2)!!!!");
        SpringApplication.run(SwimApplication.class, args);
    }

}
