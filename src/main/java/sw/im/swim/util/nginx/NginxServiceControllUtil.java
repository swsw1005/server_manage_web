package sw.im.swim.util.nginx;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.util.process.ProcessExecUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;

@Slf4j
public class NginxServiceControllUtil {

    private static final String[] NGINX_SERVICE_START = new String[]{"sh", "-c", "systemctl start nginx"};
    private static final String[] NGINX_SERVICE_STOP = new String[]{"sh", "-c", "systemctl stop nginx"};
    private static final String[] NGINX_SERVICE_STATUS = new String[]{"sh", "-c", "systemctl status nginx"};

    private static final String[] NGINX_STATUS_ACTIVE_INDICATORS = new String[]{"nginx.service", "Loaded: loaded", "Active: active (running)", "Main PID:"};

    private static final String[] NGINX_STATUS_DEAD_INDICATORS = new String[]{"nginx.service", "Loaded: loaded", "Active: inactive", "Main PID:"};

    public static final boolean NGINX_START() {
        return NGINX_JOB(true);
    }

    public static final boolean NGINX_STOP() {
        return NGINX_JOB(false);
    }

    public static final boolean NGINX_ALIVE() {
        try {

            String[] var2;
            var2 = NGINX_STATUS_ACTIVE_INDICATORS;

            return NGINX_STATUS_JUDGE(var2);

        } catch (Exception e) {
        }
        return false;
    }

    public static final boolean CERTBOT_INIT(final String hostDomain, Set<String> domains) {
        try {

            domains.remove(hostDomain);

            String certCommand = "";
            certCommand += " certbot --authenticator standalone --installer nginx ";
            certCommand += (" -d " + "swfa.pw" + " ");
            certCommand += (" -d " + hostDomain + " ");
            for (String domain : domains) {
                certCommand += (" -d " + domain + " ");
            }

            certCommand += " --non-interactive --agree-tos --redirect  --expand  -m swsw1005@gmail.com ";
            certCommand += " --pre-hook \"systemctl stop nginx\" --post-hook \"systemctl start nginx\" ";

            String[] CERTBOT_EXEC = new String[]{"sh", "-c", certCommand};

            String commandResult = ProcessExecUtil.RUN_READ_COMMAND(CERTBOT_EXEC);

            log.error("\t certCommand \n--------------------------------------\n"
                    + certCommand + "\n============================\n");
            log.error("\t commandResult \n--------------------------------------\n"
                    + commandResult + "\n============================\n");

        } catch (Exception e) {
        }
        return false;
    }


    private static boolean NGINX_STATUS_JUDGE(String[] var2) {
        String nginxStatus = ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_STATUS);

        final int ARR_LENGTH = var2.length;

        int[] INDEX_ARR = new int[ARR_LENGTH];

        for (int i = 0; i < ARR_LENGTH; i++) {
            INDEX_ARR[i] = nginxStatus.indexOf(var2[i]);
        }

        int pre_idx = -1;
        for (int i = 0; i < ARR_LENGTH; i++) {
            int idx = INDEX_ARR[i];
            if (pre_idx < idx) {
                pre_idx = idx;
            } else {
                return false;
            }
        }
        return true;
    }

    private static final boolean NGINX_JOB(final boolean start) {
        try {
            String[] var1;
            String[] var2;

            if (start) {
                var1 = NGINX_SERVICE_START;
                var2 = NGINX_STATUS_ACTIVE_INDICATORS;
            } else {
                var1 = NGINX_SERVICE_STOP;
                var2 = NGINX_STATUS_DEAD_INDICATORS;
            }

            String var1_result = ProcessExecUtil.RUN_READ_COMMAND(var1);

            return NGINX_STATUS_JUDGE(var2);

        } catch (Exception e) {
        }
        return false;
    }


    public static final void test() {

        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STATUS));
        log.warn(ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_STATUS));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STOP));
        log.warn(ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_STOP));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STATUS));
        log.warn(ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_STATUS));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_START));
        log.warn(ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_START));
        log.warn("-------------------------------------------------------");
        log.warn(new Gson().toJson(NGINX_SERVICE_STATUS));
        log.warn(ProcessExecUtil.RUN_READ_COMMAND(NGINX_SERVICE_STATUS));
        log.warn("-------------------------------------------------------");


    }


}
