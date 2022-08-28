package sw.im.swim.util.nginx;

import kr.swim.util.process.ProcessExecutor;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.server.PortCheckUtil;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxServiceControlUtil {
    private static final String[] NGINX_SERVICE_START = new String[]{"sh", "-c", "systemctl start nginx"};
    private static final String[] NGINX_SERVICE_RESTART = new String[]{"sh", "-c", "curl -ik -X POST 127.0.0.1:8085/api/v1/nginx/restart"};

    private static final String[] NGINX_SERVICE_STOP = new String[]{"sh", "-c", "systemctl stop nginx"};

    private static final String[] NGINX_SERVICE_STATUS = new String[]{"sh", "-c", "systemctl status nginx"};

    private static final String[] NGINX_DOCKER_STATUS = new String[]{"sh", "-c", "docker ps | grep nginx-proxy"};
    private static final String[] NGINX_DOCKER_RESTART = new String[]{"sh", "-c", "docker restart nginx-proxy"};

    private static final String[] NGINX_STATUS_ACTIVE_INDICATORS = new String[]{
            "nginx.service",
            "Loaded: loaded",
            "Active: active (running)",
            "Main PID:"};
    private static final String[] NGINX_STATUS_DEAD_INDICATORS = new String[]{
            "nginx.service",
            "Loaded: loaded",
            "Active: inactive",
            "Main PID:"};
    private static final String[] CERTBOT_RESULT_INDICATORS = new String[]{
            "Congratulations",
            "fullchain.pem",
            "privkey.pem"};


    public static final boolean checkNginxAlive() {
        try {
            return PortCheckUtil.available(80);
            
        } catch (RuntimeException e) {
            if (log.isDebugEnabled()) {
                log.error(e + " " + e.getMessage(), e);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error(e + " " + e.getMessage(), e);
            }
        }
        return false;
    }



    public static final boolean NGINX_RESTART() {
        try {
            String[] commandArr;
            if (GeneralConfig.ADMIN_SETTING.isNGINX_NATIVE()) {
                commandArr = NGINX_SERVICE_RESTART;
            } else {
                commandArr = NGINX_DOCKER_RESTART;
            }

            log.warn("nginx restart !!!  => " + new Gson().toJson(commandArr));
            String nginxRestartResult = ProcessExecutor.runSimpleCommand(commandArr);
            log.warn("nginx restart result => " + nginxRestartResult);
            return true;
        } catch (RuntimeException e) {
            log.error(e + "   " + e.getMessage(), e);
        } catch (Exception e) {
            log.error(e + "   " + e.getMessage(), e);
        }
        return false;
    }


}
