package sw.im.swim.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.process.ProcessExecUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class CertDateUtil {


    public static final Calendar[] GET_CERT_DATE() {
        return GET_CERT_DATE(GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN(), 443);
    }

    public static final Calendar[] GET_CERT_DATE(final String domain) {
        return GET_CERT_DATE(domain, 443);
    }

    public static final Calendar[] GET_CERT_DATE(final String domain, final int port) {

        try {

            final String cmd = "echo | openssl s_client " + "-servername " + domain + "-connect " + domain + ":" + port + " 2>/dev/null | openssl x509 -noout -dates";

            log.debug("EXECUTE  >>  " + cmd);

            List<String> arr = ProcessExecUtil.RUN_READ_COMMAND_LIST(new String[]{"sh", "-c", cmd});

            log.info("cert result : " + new Gson().toJson(arr));

            String startDateStr = arr.get(0).replace("notBefore=", "");
            String endDateStr = arr.get(1).replace("notAfter=", "");

            Calendar startDate = PARSE_CERT_DATE(startDateStr);
            Calendar endDate = PARSE_CERT_DATE(endDateStr);

            GeneralConfig.CERT_EXPIRED_AT = endDate;

            return new Calendar[]{startDate, endDate};

        } catch (IndexOutOfBoundsException e) {
            log.error(e + "  " + e.getMessage());
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
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
