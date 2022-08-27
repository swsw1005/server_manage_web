package sw.im.swim.util.server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicIpInfoUtil {
    public static final PublicIpInfo GET_PUBLIC_IP() {

        String IP_GET_URL = "http://ipinfo.io";

        PublicIpInfo publicIpInfo = new PublicIpInfo();

        String[] arr = {
                "ip", "city", "region", "country", "loc", "org", "timezone", "postal"
        };

        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {

            HttpPost httpPost = new HttpPost(IP_GET_URL);

            response = httpClient.execute(httpPost);

            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            log.debug("ip body == " + body);

            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();

            publicIpInfo.setIp(jsonObject.get("ip").getAsString());
            publicIpInfo.setCity(jsonObject.get("city").getAsString());
            publicIpInfo.setRegion(jsonObject.get("region").getAsString());
            publicIpInfo.setCountry(jsonObject.get("country").getAsString());
            publicIpInfo.setLoc(jsonObject.get("loc").getAsString());
            publicIpInfo.setOrg(jsonObject.get("org").getAsString());
            publicIpInfo.setTimezone(jsonObject.get("timezone").getAsString());
            publicIpInfo.setPostal(jsonObject.get("postal").getAsString());

        } catch (Exception e) {
            log.error("----", e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
        }
        return publicIpInfo;

    }

}
