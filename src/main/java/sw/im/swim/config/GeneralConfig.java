package sw.im.swim.config;

import kr.swim.util.cert.CertDateUtil;
import kr.swim.util.system.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.CronVO;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.util.server.PublicIpInfo;
import sw.im.swim.util.server.PublicIpInfoUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
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

    public static final String USER_HOME_DIR(final String suffix) {

        String var1 = USER_HOME_DIR();

        if (var1 == null) {
            return null;
        }

        return var1 + "/" + suffix.trim();
    }

    public static final String USER_HOME_DIR() {
        try {
            final String user = GeneralConfig.ADMIN_SETTING.getSERVER_MANAGER_USER().trim().toLowerCase();

            if (user.equals("root")) {
                return "/root";
            } else {
                return "/home/" + user;
            }
        } catch (NullPointerException e) {
            log.warn("null pointer exception...");
        } catch (Exception e) {
            log.warn("unknown exception...", e);
        }
        return null;
    }

    public static final ConcurrentHashMap<String, Boolean> NOTI_SETTING_MAP = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<Long, NotiEntityDto> NOTI_DTO_MAP = new ConcurrentHashMap<>();

    public static final Vector<CronVO> CRON_EXPRESSION_LIST = new Vector<>();

    public static SystemInfo SERVER_INFO = null;

    public static PublicIpInfo PUBLIC_IP_INFO = PublicIpInfoUtil.GET_PUBLIC_IP();

    public static ServerInfoEntityDto CURRENT_SERVER_INFO;

    public static String CERT_FILE_FULLCHAIN = "fullchain.pem";
    public static String CERT_FILE_PRIKEY = "privkey.pem";
    public static String CERT_FILE_CHAIN = "chain.pem";


    public static Calendar CERT_STARTED_AT = null;
    public static Calendar CERT_EXPIRED_AT = null;

    public static void setCertDate() {
        try {
            Calendar[] a = CertDateUtil.getCertDates(GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN());

            GeneralConfig.CERT_STARTED_AT = a[0];
            GeneralConfig.CERT_EXPIRED_AT = a[1];

        } catch (Exception e) {
            log.error(e + "  " + e.getMessage());
        }
    }

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

    public static final String NGINX_LOG_FORMAT_DEFAULT = "[$time_iso8601] " + "| $remote_addr | $remote_user  $scheme://$host$request_uri" + " \"$request\" $status $body_bytes_sent -" + " \"$http_referer\" \"$http_user_agent\" \"$request_time\"";
}
