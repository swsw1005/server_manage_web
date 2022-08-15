package sw.im.swim.config;

import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.CronVO;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.util.server.ServerInfoUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GeneralConfig {

//    public static final Hashtable<NotiType, Boolean> booleanMap = new Hashtable<>();

    public static final boolean WINDOW = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH).contains("window");

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Seoul");

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String GOOGLE_DNS_USER_NAME;
    public static String GOOGLE_DNS_PASSWORD;
    public static String CURRENT_IP = "";

    public static String ENC_KEY = "";


    public static AdminSettingEntityDto ADMIN_SETTING;

    public static final ConcurrentHashMap<String, Boolean> NOTI_SETTING_MAP = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<Long, NotiEntityDto> NOTI_DTO_MAP = new ConcurrentHashMap<>();

    public static final Vector<CronVO> CRON_EXPRESSION_LIST = new Vector<>();

    public static ServerInfoUtil.ServerInfo SERVER_INFO = ServerInfoUtil.getServerInfo();

    public static ServerInfoUtil.PublicIpInfo PUBLIC_IP_INFO = ServerInfoUtil.GET_PUBLIC_IP();

    public static ServerInfoEntityDto CURRENT_SERVER_INFO;

    public static String CERT_FILE_FULLCHAIN = "fullchain.pem";
    public static String CERT_FILE_PRIKEY = "privkey.pem";
    public static String CERT_FILE_CHAIN = "chain.pem";


    public static Calendar CERT_STARTED_AT = null;
    public static Calendar CERT_EXPIRED_AT = null;

    public static int CERT_LEFT_DAY() {
        try {

            Calendar NOW = Calendar.getInstance(CERT_EXPIRED_AT.getTimeZone());

            long var1 = NOW.getTimeInMillis();
            long var2 = CERT_EXPIRED_AT.getTimeInMillis();

            long leftTimeMills = var2 - var1;

            long a = TimeUnit.DAYS.convert(leftTimeMills, TimeUnit.MILLISECONDS);

            int aa = (int) a;

            return aa;

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error(e + "  " + e.getMessage(), e);
            } else {
                log.warn(e + "  " + e.getMessage());
            }
        }
        return 9999;
    }

    public static final String NGINX_LOG_FORMAT_DEFAULT = "[$time_iso8601] " +
            "| $remote_addr | $remote_user  $scheme://$host$request_uri" +
            " \"$request\" $status $body_bytes_sent -" +
            " \"$http_referer\" \"$http_user_agent\" \"$request_time\"";
}
