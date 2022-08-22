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

    public static final Calendar[] GET_CERT_DATE(final String domain) {

        try {

            String fileName = "";

            String var1 = GeneralConfig.CERT_FILE_FULLCHAIN;

            if (new File(var1).exists()) {
                fileName = var1;
            } else {
                fileName = NginxConfStringContext.CERT_FILE_PREFIX + "/" + domain + "/" + NginxConfStringContext.CERT_FILE_FULLCHAIN;
            }

            final String cmd = "openssl x509 -startdate -enddate -in " + fileName;

            log.warn("EXECUTE  >>  " + cmd);

            List<String> arr = ProcessExecUtil.RUN_READ_COMMAND_LIST(new String[]{"sh", "-c", cmd});

            log.warn("cert result : " + new Gson().toJson(arr));

            String startDateStr = arr.get(0).replace("notBefore=", "");
            String endDateStr = arr.get(1).replace("notAfter=", "");

            Calendar startDate = PARSE_CERT_DATE(startDateStr);
            Calendar endDate = PARSE_CERT_DATE(endDateStr);

            GeneralConfig.CERT_STARTED_AT = startDate;
            GeneralConfig.CERT_EXPIRED_AT = endDate;

            return new Calendar[]{startDate, endDate};

        } catch (IndexOutOfBoundsException e) {
            log.error(e + "  " + e.getMessage());
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }
        if (GeneralConfig.WINDOW) {
            return WINDOW_FAKE_CERT();
        }
        return null;
    }

    public static final SimpleDateFormat CERT_DATE_FORMAT = new SimpleDateFormat("LLL dd HH:mm:ss yyyy z", Locale.ENGLISH);

    public static final TimeZone CERT_TIMEZONE = TimeZone.getTimeZone("GMT");

    public static final Calendar PARSE_CERT_DATE(String dateStr) throws ParseException {
        CERT_DATE_FORMAT.setTimeZone(CERT_TIMEZONE);
        Date date = CERT_DATE_FORMAT.parse(dateStr);
        Calendar cal = Calendar.getInstance(CERT_TIMEZONE);
        cal.setTime(date);
        return cal;
    }

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

        System.out.println("CERT_DATE_FORMAT.format(new Date()) = " + CERT_DATE_FORMAT.format(new Date()));

        String[] arr = {"Jul 31 05:20:46 2022 GMT", "Oct 29 05:20:45 2022 GMT"};

        for (String input : arr) {

            try {
                Calendar parsed = CertDateUtil.PARSE_CERT_DATE(input);

                String parsed_format = CERT_DATE_FORMAT.format(parsed.getTime());

                System.out.println("input         = " + input);
                System.out.println("parsed_format = " + parsed_format);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
