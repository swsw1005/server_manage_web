package sw.im.swim;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import lombok.extern.slf4j.Slf4j;
import sw.im.swim.component.ExternalCertCheckJob;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ExternalCertCheckJobTest {

	@Test
	public void testExecuteInternal() {
		String[] urls = {
			"https://www.google.com",
			"https://www.naver.com",
			"https://www.daum.net",
			"https://swsw1005.com",
			"https://swsw1005.com:11443",
			"https://admin.swsw1005.com:11443",
			"https://ha.swsw1005.com:8321",
			"https://ha.swsw1005.com:11443",
		};

		for (String url : urls) {
			try {
				log.info("url [{}] check", url);
				ExternalCertCheckJob.checkSSL(url);
			} catch (Exception e) {
				log.error("url [{}], {}", url, e.getMessage());
			}
		}

	}

}
