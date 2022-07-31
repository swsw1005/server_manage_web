package sw.im.swim.worker.nginx;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.nginx.NginxConfCreateUtil;
import sw.im.swim.util.nginx.NginxServiceControllUtil;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <PRE>
 * 1. certbot DNS 방식으로 기존 발급된 인증서 있음
 * 2. 정해진 위치의 pem 파일 이용해서 nginx 작성
 * </PRE>
 */
@Slf4j
@RequiredArgsConstructor
public class NginxV2Worker implements Runnable {

    private final NginxPolicyEntityDto policyEntityDto;
    private final List<NginxServerEntityDto> nginxServerEntityList;
    private final AdminLogService adminLogService;

//    private String OLD_NGINX_CONF_FILE_PATH = "";

    private static final String NGINX_CONF_DIR = "/etc/nginx";

    private static final String NGINX_SITES_DIR = NGINX_CONF_DIR + "/sites-enabled";
    private static final String NGINX_CONF_FILE = "nginx.conf";

    private static final String UPDATE_TITLE = "NGINX UPDATE";
    private static final String RESTORE_TITLE = "NGINX RESTORE";

    @Override
    public void run() {

        log.info("\n" + "=======================================" + "\n" + "START NGINX JOB !!!!!!" + "\n" + "=======================================");

        try {
            FileUtils.forceMkdir(new File(NGINX_CONF_DIR));
            FileUtils.forceMkdir(new File(NGINX_SITES_DIR));

            File OLD_NGINX_CONF = new File(NGINX_CONF_DIR + "/" + NGINX_CONF_FILE);
            OLD_NGINX_CONF.delete();

            File NEW_NGINX_CONF = new File(NGINX_CONF_DIR + "/" + NGINX_CONF_FILE);
            NEW_NGINX_CONF.createNewFile();

            // 기본 nginx conf 설정
            List<String> defaultNginxConfText = NginxConfCreateUtil.DEFAULT_NGINX_CONF(policyEntityDto);
            FileUtils.writeLines(NEW_NGINX_CONF, StandardCharsets.UTF_8.name(),
                    ///////////////////////////////////////////////////////////
                    defaultNginxConfText, System.lineSeparator());

            // 기존 sites-enable 삭제
            FileUtils.cleanDirectory(new File(NGINX_SITES_DIR));

            // 새로운 sites-enable 작성
            NginxConfCreateUtil.CREATE_SITES_ENABLES(NGINX_SITES_DIR, policyEntityDto, nginxServerEntityList);

            log.warn("nginx 재시작 !");

            // 재시작
            NginxServiceControllUtil.NGINX_RESTART();

            // nginx 상태 확인
            final boolean isNginxAlive = NginxServiceControllUtil.NGINX_ALIVE();

            log.warn("nginx 재시작 후 상태 :: " + isNginxAlive);

            adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "SUCCESS", "NGINX UPDATE END");

        } catch (Exception e) {
            log.error(e.getMessage() + " | " + e.getLocalizedMessage());

            // 기존 sites-enable 삭제
            try {
                FileUtils.cleanDirectory(new File(NGINX_SITES_DIR));

                log.warn("nginx 재시작 !");
                // 재시작
                NginxServiceControllUtil.NGINX_RESTART();

            } catch (Exception ex) {
                log.error("nginx 복구중 에러 :: " + e + "  " + e.getMessage(), e);
            }
        } finally {
            log.info("\n" + "=======================================" + "\n" + "FINALIZE NGINX JOB !!!!!!" + "\n" + "=======================================");
        }

    }


}
