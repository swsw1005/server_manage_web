package sw.im.swim.worker.noti;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.bean.enums.NotiType;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NotiWorker implements Runnable {

    public static final String SLACK_NOTI_URL = "https://slack.com/api/chat.postMessage";

    public static final int RETRY = 3;

    private final NotiEntityDto notiEntityDto;

    private final String msg;

    private CloseableHttpResponse response = null;
    private CloseableHttpClient httpClient = null;


    @Override
    public void run() {

        NotiType NOTI_TYPE = notiEntityDto.getNotiType();

        log.info("NOTI_TYPE==" + NOTI_TYPE + " __ " + notiEntityDto.getName());

        switch (NOTI_TYPE) {
            case SLACK:
                slack();
                break;
            case NATEON:
                nateon();
                break;
        }
    }


    private final void slack() {

        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(SLACK_NOTI_URL);

            String token = notiEntityDto.getColumn1();
            String channelId = notiEntityDto.getColumn2();

            httpPost.setHeader("Authorization", "Bearer " + token);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; utf-8");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("channel", channelId));
            params.add(new BasicNameValuePair("text", msg));
            HttpEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
            httpPost.setEntity(formEntity);

            for (int i = 0; i < RETRY; i++) {
                try {
                    response = httpClient.execute(httpPost);

                    ResponseHandler<String> handler = new BasicResponseHandler();
                    String body = handler.handleResponse(response);

                    JsonObject jsonObj = JsonParser.parseString(body).getAsJsonObject();

                    boolean bodyOk = jsonObj.get("ok").getAsBoolean();

                    if (bodyOk) {
                        break;
                    }
                } catch (Exception e) {
                    log.error("---------" + e.getMessage(), e);
                }
            }

        } catch (Exception e) {
            log.error("---------" + e.getMessage(), e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
            try {
                httpClient.close();
            } catch (Exception e) {
            }
        }

    }

    private final void nateon() {

        try {
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(notiEntityDto.getColumn1());

            try {

                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; utf-8");
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("content", msg));
                HttpEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
                httpPost.setEntity(formEntity);

                for (int i = 0; i < RETRY; i++) {
                    try {
                        response = httpClient.execute(httpPost);
                        int responseCode = response.getStatusLine().getStatusCode();

                        if (200 <= responseCode && responseCode <= 299) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }

            } catch (Exception e) {
                log.error("---------" + e.getMessage(), e);
            }

        } catch (Exception e) {
            log.error("---------" + e.getMessage(), e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
            try {
                httpClient.close();
            } catch (Exception e) {
            }
        }
    }


}
