package sw.im.swim.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.dns.GoogleDNSUtil;

import java.io.File;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class PostConstruct {

    @Value("${google.dns.username}")
    private String GOOGLE_DNS_USER_NAME;

    @Value("${google.dns.password}")
    private String GOOGLE_DNS_PASSWORD;

    private final AdminLogService adminLogService;

//    USER_NAME=VUEKfrgtRxYM02Uh
//            PASSWORD=HKLSuJ41rME4DwRu

    @javax.annotation.PostConstruct
    public void INIT() {

        String ip = GoogleDNSUtil.getInstance().GET_IP();

        adminLogService.insertLog("START", "IP", ip);

        GeneralConfig.GOOGLE_DNS_USER_NAME = GOOGLE_DNS_USER_NAME;
        GeneralConfig.GOOGLE_DNS_PASSWORD = GOOGLE_DNS_PASSWORD;
        GeneralConfig.CURRENT_IP = ip;

        try {
            new File(DatabaseServerUtil.DB_DUMP_FILE_TMP).delete();
        } catch (Exception e) {
        }

    }
}
