package sw.im.swim.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NginxWorker implements Runnable {

    private final NginxPolicyEntityDto policyEntityDto;
    private final List<NginxServerEntityDto> nginxServerEntityList;

    private String OLD_NGINX_CONF_FILE_PATH = "";

    private static final String NGINX_CONF_DIR = "/etc/nginx";
    private static final String NGINX_CONF_FILE = "nginx.conf";


    @Override
    public void run() {

        try {

            log.info("\n" + "======================================="
                    + "\n" + "START NGINX JOB !!!!!!"
                    + "\n" + "======================================="
            );

            Thread.sleep(5000);




        } catch (Exception e) {

        } finally {

            log.info("\n" + "======================================="
                    + "\n" + "FINALIZE NGINX JOB !!!!!!"
                    + "\n" + "======================================="
            );
        }

    }
}
