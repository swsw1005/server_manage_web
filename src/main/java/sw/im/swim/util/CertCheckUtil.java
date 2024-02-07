package sw.im.swim.util;

import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

import lombok.Builder;
import lombok.Getter;

public class CertCheckUtil {
	public static CertDateInfo check(final String host) throws IOException {
		return check(host, 443);
	}

	public static CertDateInfo check(final String host, final int port) throws IOException {
		try {
			URL destinationURL = new URL("https://" + host + ":" + port);
			HttpsURLConnection conn = (HttpsURLConnection)destinationURL.openConnection();
			conn.connect();
			Certificate[] certs = conn.getServerCertificates();

			Certificate cert = certs[0];
			if (cert instanceof X509Certificate) {
				System.out.println("-----------------------------");
				X509Certificate x = (X509Certificate)cert;
				Date beforeDate = x.getNotBefore();
				Date afterDate = x.getNotAfter();
				TimeZone tz = TimeZone.getDefault();

				Calendar beforeCal = Calendar.getInstance(tz);
				Calendar afterCal = Calendar.getInstance(tz);
				beforeCal.setTime(beforeDate);
				afterCal.setTime(afterDate);

				System.out.println("beforeDate = " + beforeDate);
				System.out.println("afterDate = " + afterDate);

				return CertDateInfo.builder()
					.start(beforeCal)
					.end(afterCal)
					.sigAlgName(x.getSigAlgName())
					.sigAlgOID(x.getSigAlgOID())
					.type(x.getType())
					.build();
			}
		} catch (Exception e) {

		} finally {

		}
		return null;
	}

	@Getter
	@Builder
	public static class CertDateInfo {
		private String sigAlgName;
		private String sigAlgOID;
		private String type;
		private Calendar start;
		private Calendar end;
	}
}
