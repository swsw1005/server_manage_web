package sw.im.swim.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Service;
import sw.im.swim.config.GeneralConfig;

@Service
public class Fail2banService {

    public int SEND(String server, String ip, String country, String job) {

        List<String> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String now = sdf.format(new Date());
        list.add(now);

        String CHANNEL_ID = "";

        switch (job.toLowerCase()) {
            case "start":
                list.add("FAIL2BAN START !!!");
                list.add("\t\t server :: " + server);
                break;
            case "stop":
                list.add("FAIL2BAN STOP !!!");
                list.add("\t\t server :: " + server);
                break;
            case "ban":
                list.add(" FAIL2BAN 차단!!!!!");
                list.add("\t\t server :: " + server);
                list.add("\t\t IP :: " + ip);
                list.add("\t\t 국가 :: " + country);
                break;
            case "unban":
                list.add(" FAIL2BAN 차단해제");
                list.add("\t\t server :: " + server);
                list.add("\t\t IP :: " + ip);
                list.add("\t\t 국가 :: " + country);
                break;

            default:
                return 0;
        }
        list.add("");

        boolean bool = true;

        if (bool) {
            return 1;
        }

        return 0;
    }

}
