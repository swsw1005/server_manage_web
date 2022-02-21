package sw.im.swim.config;

import org.springframework.beans.factory.annotation.Value;
import sw.im.swim.bean.dto.AdminSettingEntityDto;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class GeneralConfig {

    public static String FAIL2BAN_TOKEN = "ahlf3oia9a83fghlia938g9";

//    public static final Hashtable<NotiType, Boolean> booleanMap = new Hashtable<>();

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Seoul");

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String GOOGLE_DNS_USER_NAME;
    public static String GOOGLE_DNS_PASSWORD;
    public static String CURRENT_IP = "";

    public static String ENC_KEY = "";

    public static AdminSettingEntityDto ADMIN_SETTING;

}
