package sw.im.swim.util.nginx;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.util.process.ProcessExecUtil;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxServiceControlUtil {
    private static final String[] NGINX_SERVICE_START = new String[]{"sh", "-c", "systemctl start nginx"};
    private static final String[] NGINX_SERVICE_RESTART = new String[]{"sh", "-c", "service nginx restart"};
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

            String[] var2;
            var2 = NGINX_STATUS_ACTIVE_INDICATORS;

            return NGINX_STATUS_JUDGE(var2);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error(e + " " + e.getMessage(), e);
            }
        }
        return false;
    }


    private static boolean NGINX_STATUS_JUDGE(String[] var2) {
        String nginxStatus = ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_STATUS);

        final int ARR_LENGTH = var2.length;

        int[] indexArr = new int[ARR_LENGTH];

        for (int i = 0; i < ARR_LENGTH; i++) {
            indexArr[i] = nginxStatus.indexOf(var2[i]);
        }

        int pre_idx = -1;
        for (int i = 0; i < ARR_LENGTH; i++) {
            int idx = indexArr[i];
            if (pre_idx < idx) {
                pre_idx = idx;
            } else {
                return false;
            }
        }
        return true;
    }


    public static final boolean NGINX_RESTART() {
        try {
            log.warn("nginx restart !!!  => " + new Gson().toJson(NGINX_DOCKER_RESTART));
            String nginxRestartResult = ProcessExecUtil.RUN_READ_COMMAND(NGINX_DOCKER_RESTART);
            log.warn("nginx restart result => " + nginxRestartResult);
            return true;
        } catch (Exception e) {
            log.error(e + "   " + e.getMessage(), e);
        }
        return false;
    }


}
