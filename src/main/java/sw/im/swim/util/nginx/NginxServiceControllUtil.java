package sw.im.swim.util.nginx;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class NginxServiceControllUtil {

    private static final String[] NGINX_SERVICE_START = new String[]{"sh", "-c", "systemctl start nginx"};
    private static final String[] NGINX_SERVICE_STOP = new String[]{"sh", "-c", "systemctl stop nginx"};
    private static final String[] NGINX_SERVICE_STATUS = new String[]{"sh", "-c", "systemctl status nginx"};

    public static final String RUN_READ_COMMAND(final String[] commandArr) {
        String cli = "";
        try {
            Process p = Runtime.getRuntime().exec(commandArr);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                cli += "\n";
                cli += line;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return cli;
    }


    public static final void test() {


        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STATUS));
        log.warn(RUN_READ_COMMAND(NGINX_SERVICE_STATUS));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STOP));
        log.warn(RUN_READ_COMMAND(NGINX_SERVICE_STOP));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STATUS));
        log.warn(RUN_READ_COMMAND(NGINX_SERVICE_STATUS));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_START));
        log.warn(RUN_READ_COMMAND(NGINX_SERVICE_START));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STATUS));
        log.warn(RUN_READ_COMMAND(NGINX_SERVICE_STATUS));
        log.warn("-------------------------------------------------------");


    }


}
