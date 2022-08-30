package sw.im.swim.util.dns;

import kr.swim.util.enc.AesUtils;
import kr.swim.util.enc.EncodingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import sw.im.swim.config.GeneralConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GoogleDNSUtil {

    public static final String CHECK_IP_ADDRESS = "https://domains.google.com/checkip";

    public String GET_IP() {

        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {

            HttpGet http = new HttpGet(CHECK_IP_ADDRESS);

            response = httpClient.execute(http);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response).trim();

            String[] bodyArr = body.split("\\.");
            int[] intArr = new int[4];

            for (int i = 0; i < 4; i++) {
                intArr[i] = Integer.parseInt(bodyArr[i]);
            }

            String ip = "";

            for (int i = 0; i < intArr.length; i++) {

                if (i > 0) {
                    ip += ".";
                }
                ip += intArr[i];
            }
            return ip;

        } catch (Exception e) {
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
        }
        log.error("GET IP FAIL !!!!! RETURN NULL");
        return null;
    }


    public void updateIp(final String hostname) throws Exception {
        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {

            String url =
                    "https://" +
                            GeneralConfig.GOOGLE_DNS_USER_NAME +
                            ":" +
                            GeneralConfig.GOOGLE_DNS_PASSWORD +
                            "@domains.google.com/nic/update";

            log.info(" ===== GOOGLE Dynamic DNS =======");
            log.info("updateIp url => " + url);

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; utf-8");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("hostname", hostname));
            params.add(new BasicNameValuePair("myip", GeneralConfig.CURRENT_IP));

//            hostname=${HOST_NAME}&myip=${IP}

            HttpEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(formEntity);

            response = httpClient.execute(httpPost);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            log.info("body => " + body);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
        }
    }


    private GoogleDNSUtil() {
    }

    private static class SingleTone {
        public static final GoogleDNSUtil INSTANCE = new GoogleDNSUtil();
    }

    public static GoogleDNSUtil getInstance() {
        return SingleTone.INSTANCE;
    }

}
