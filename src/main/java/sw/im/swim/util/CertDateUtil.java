package sw.im.swim.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.date.DateFormatUtil;
import sw.im.swim.util.nginx.NginxConfCreateUtil;
import sw.im.swim.util.nginx.NginxConfStringContext;
import sw.im.swim.util.process.ProcessExecUtil;
import sw.im.swim.util.server.SSLCertCheckUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CertDateUtil {


    public static final Calendar[] GET_CERT_DATE() {
//        return GET_CERT_DATE(GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN());
        try {
            SSLCertCheckUtils.CertInfo var1 = SSLCertCheckUtils.sslCheck(GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN());

            GeneralConfig.CERT_STARTED_AT = var1.getBefore();
            GeneralConfig.CERT_EXPIRED_AT = var1.getAfter();

            return new Calendar[]{var1.getBefore(), var1.getAfter()};
        } catch (RuntimeException e) {
            log.warn(e + " | " + e.getMessage());
        } catch (Exception e) {
            log.warn(e + " | " + e.getMessage());
        }
        return WINDOW_FAKE_CERT();
    }

    public static final TimeZone CERT_TIMEZONE = TimeZone.getTimeZone("GMT");


    public static final Calendar[] WINDOW_FAKE_CERT() {

        final int interval = 10;

        Calendar cal1 = Calendar.getInstance(CERT_TIMEZONE);
        Calendar cal2 = Calendar.getInstance(CERT_TIMEZONE);

        cal2.add(Calendar.DAY_OF_MONTH, interval);
        cal1.add(Calendar.DAY_OF_MONTH, interval - 90);

        String date1 = DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss_z.format(cal1.getTime());
        String date2 = DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss_z.format(cal2.getTime());

        log.debug("fake cert date for window");
        log.debug("  " + date1 + " ~ " + date2);

        GeneralConfig.CERT_STARTED_AT = cal1;
        GeneralConfig.CERT_EXPIRED_AT = cal2;

        return new Calendar[]{cal1, cal2};
    }


    public static void main(String[] args) {


    }
}
