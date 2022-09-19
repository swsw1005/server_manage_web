package sw.im.swim;

import kr.swim.util.enc.EncodingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@SpringBootTest
class SwimApplicationTests {

	@Test
	void contextLoads() throws EncodingException, NoSuchAlgorithmException {

		for (int i = 0; i < 20; i++) {

			final int a = SecureRandom.getInstanceStrong().nextInt();
			System.out.println("a = " + a);
			final String b = String.valueOf(a);

			System.out.println("b = " + b);
			final int random = Integer.parseInt(b.substring(1, 2));

			System.out.println("random = " + random);
		}
	}

}
