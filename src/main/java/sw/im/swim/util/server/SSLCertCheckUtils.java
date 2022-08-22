package sw.im.swim.util.server;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Time;
import java.util.*;

@Slf4j
public class SSLCertCheckUtils {

    static final String https = "https://";

    static final String exMsg = "ssl check exception :: ";

    public static CertInfo sslCheck(final String host) throws NoSuchAlgorithmException, IOException, KeyStoreException, KeyManagementException, CertificateException {

        try {
            if (host == null || host.isEmpty()) {
                throw new InvalidParameterException(exMsg + "host is null");
            }

            final String url;

            if (host.startsWith(https)) {
                url = host;
            } else {
                url = https + host;
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager passthroughTrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }


                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{passthroughTrustManager}, null);


            SSLSocketFactory ssf = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) ssf.createSocket(host, 443);
            socket.startHandshake();

            X509Certificate[] peerCertificates = (X509Certificate[]) socket.getSession().getPeerCertificates();

            // By default on Oracle JRE, algorithm is PKIX
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            // 'null' will initialise the tmf with the default CA certs installed
            // with the JRE.
            tmf.init((KeyStore) null);
            X509TrustManager tm = (X509TrustManager) tmf.getTrustManagers()[0];

            // Assuming RSA key here.
            tm.checkServerTrusted(peerCertificates, "RSA");

            X509Certificate serverCert = peerCertificates[0];

            Date beforeDate = serverCert.getNotBefore();
            Date afterDate = serverCert.getNotAfter();

            int var1 = serverCert.getBasicConstraints();
            int var2 = serverCert.getVersion();
            List<String> var3 = serverCert.getExtendedKeyUsage();
            Collection<List<?>> var4 = serverCert.getIssuerAlternativeNames();
            BigInteger var5 = serverCert.getSerialNumber();
            String var6 = serverCert.getSigAlgName();
            String var7 = serverCert.getSigAlgOID();
            byte[] var8 = serverCert.getSignature();
            String var9 = serverCert.getType();

            System.out.println("beforeDate = " + beforeDate);
            System.out.println("afterDate = " + afterDate);

            CertInfo certInfo = new CertInfo();

            certInfo.setBasicConstraints(var1);
            certInfo.setVersion(var2);
            certInfo.setExtendedKeyUsage(var3);
            certInfo.setSerialNumber(var5);
            certInfo.setSigAlgName(var6);
            certInfo.setSigAlgOID(var7);
            certInfo.setSignature(var8);
            certInfo.setType(var9);

            TimeZone tz = TimeZone.getDefault();

            log.debug("tz = " + tz.getDisplayName());

            Calendar beforeCal = Calendar.getInstance(tz);
            Calendar afterCal = Calendar.getInstance(tz);

            beforeCal.setTime(beforeDate);
            afterCal.setTime(afterDate);

            certInfo.setBefore(beforeCal);
            certInfo.setAfter(afterCal);

            return certInfo;

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            // Here you may check which subclass of CertificateException to know what the error is.
            log.error(e + "  " + e.getMessage(), e);
            throw new CertificateException(e);
        } catch (IOException e) {
            log.error(e + "  " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
            throw e;
        }

    }

    @Getter
    @Setter
    public static class CertInfo {
        int basicConstraints;
        int version;
        List<String> extendedKeyUsage;
        BigInteger serialNumber;
        String sigAlgName;
        String sigAlgOID;
        byte[] signature;
        String type;
        Calendar before;
        Calendar after;
    }


    public static void main(String[] args) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, KeyManagementException {
        CertInfo certInfo = SSLCertCheckUtils.sslCheck("swim-playground.com");

        System.out.println("new Gson().toJson(certInfo) = " + new Gson().toJson(certInfo));

    }

}
