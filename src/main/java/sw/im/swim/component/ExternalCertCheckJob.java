package sw.im.swim.component;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.noti.NotiProducer;

@Slf4j
public class ExternalCertCheckJob implements Job {

	public static void checkSSL(String urlString) throws Exception {
		try {

			if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
			} else {
				urlString = "https://" + urlString;
			}

			URL url = new URL(urlString);
			HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
			connection.setSSLSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(2000);
			connection.connect();

			Certificate[] certs = connection.getServerCertificates();
			if (certs.length == 0 || !(certs[0] instanceof X509Certificate)) {
				throw new SSLException("서버에서 X509 인증서를 제공하지 않습니다.");
			}

			X509Certificate cert = (X509Certificate)certs[0];
			checkCertificateValidity(cert, urlString, 30);

		} catch (SSLHandshakeException e) {
			throw new SSLException("SSL 핸드셰이크 실패: 인증서가 올바르지 않거나 신뢰할 수 없습니다.", e);
		} catch (SSLException e) {
			throw e;
		} catch (IOException e) {
			throw new SSLException("SSL 연결 중 오류 발생: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new SSLException("SSL 연결 중 오류 발생: " + e.getMessage(), e);
		}
	}

	private static void checkCertificateValidity(X509Certificate cert, String url, int expiryThresholdDays) throws
		Exception {
		try {
			cert.checkValidity(); // 유효한 인증서인지 확인
			Instant startDate = cert.getNotBefore().toInstant();
			Instant expiryDate = cert.getNotAfter().toInstant();
			Instant now = Instant.now();
			long daysLeft = ChronoUnit.DAYS.between(now, expiryDate);
			ZoneId zoneId = ZoneId.of("Asia/Seoul");

			log.info("check certificate validity: \n\t [{}] start={}, expiry={}, now={}, daysLeft={}",
				url,
				startDate.atZone(zoneId).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				expiryDate.atZone(zoneId).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				now.atZone(zoneId).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				daysLeft);

			if (daysLeft < expiryThresholdDays) {
				log.warn("⚠ SSL 인증서가 " + daysLeft + "일 후 만료됩니다.");
			}

		} catch (Exception e) {
			throw new SSLException("SSL 인증서가 만료되었거나 유효하지 않습니다: " + e.getMessage(), e);
		}
	}

	private static final void noti(String message) {
		NotiProducer notiProducer = new NotiProducer(message, AdminLogType.CERTBOT);
		ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(notiProducer);
	}

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Arrays.stream(StringUtils.split(GeneralConfig.ADMIN_SETTING.getEXTERNAL_CERT_CHECK_URLS(), "\n"))
			.map(StringUtils::trim)
			.filter(StringUtils::isNotBlank)
			.forEach(url -> {
				log.debug("Check URL : " + url);

				try {
					checkSSL(url);
				} catch (Exception e) {
					log.error("SSL 인증서 확인 중 오류 발생: " + e.getMessage(), e);
					noti("[" + url + "]" + e.getMessage());
				}
			});
	}
}
