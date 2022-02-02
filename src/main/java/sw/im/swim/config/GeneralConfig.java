package sw.im.swim.config;

import sw.im.swim.bean.enums.NotiType;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.TimeZone;

public class GeneralConfig {

    public static String FAIL2BAN_TOKEN = "ahlf3oia9a83fghlia938g9";

    public Hashtable<NotiType, Boolean> booleanMap = new Hashtable<>();

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Seoul");

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
