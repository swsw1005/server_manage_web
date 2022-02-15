package sw.im.swim.util.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class DateFormatUtil {

    public static final SimpleDateFormat DATE_FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat DATE_FORMAT_yyyyMMdd_HHmmss = new SimpleDateFormat("yyyyMMdd_HHmmss");
    public static final SimpleDateFormat DATE_FORMAT_HH_mm_ss = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_yyyyMMdd_T_HHmmssXXX = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    public static final SimpleDateFormat DATE_FORMAT_yyyyMMdd_HHmmss_z = new SimpleDateFormat("yyyyMMdd_HHmmss_z");
    public static final SimpleDateFormat DATE_FORMAT_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

}
