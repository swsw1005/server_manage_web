package sw.im.swim.worker.nginx;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.date.DateFormatUtil;
import sw.im.swim.util.nginx.NginxConfCreateUtil;
import sw.im.swim.util.nginx.NginxServiceControllUtil;

@Slf4j
@RequiredArgsConstructor
public class NginxWorker implements Runnable {

    private final NginxPolicyEntityDto policyEntityDto;
    private final List<NginxServerEntityDto> nginxServerEntityList;
    private final AdminLogService adminLogService;

//    private String OLD_NGINX_CONF_FILE_PATH = "";

    private static final String NGINX_CONF_DIR = "/etc/nginx";
    private static final String NGINX_CONF_FILE = "nginx.conf";

    private static final String UPDATE_TITLE = "NGINX UPDATE";
    private static final String RESTORE_TITLE = "NGINX RESTORE";

    @Override
    public void run() {

        boolean RESTORE_NEED = false;
        String OLD_CONF_BACKUP_FILE = "";
        File NGINX_CONF_ORIGIN = null;

        int WORK_STAGE = 0;

        try {

            // Thread.sleep(111);

            log.info("\n" + "=======================================" + "\n" + "START NGINX JOB !!!!!!" + "\n" + "=======================================");

            NGINX_CONF_ORIGIN = new File(NGINX_CONF_DIR + "/" + NGINX_CONF_FILE);

            if (NGINX_CONF_ORIGIN.exists() && NGINX_CONF_ORIGIN.isFile()) {
                log.info(NGINX_CONF_ORIGIN.getAbsolutePath() + " => " + NGINX_CONF_ORIGIN.exists());
            } else {
                log.error("NGINX_CONF_ORIGIN not EXIST...");
                adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "FAIL", "ORIGIN CONF FILE NOT EXIST");
                throw new Exception("nginx conf file not exist");
            }

            final String TIMESTAMP = DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss.format(new Date());

            OLD_CONF_BACKUP_FILE = NGINX_CONF_DIR + "/" + NGINX_CONF_FILE + "_" + TIMESTAMP;

            final File confBackup = new File(OLD_CONF_BACKUP_FILE);

            FileUtils.moveFile(NGINX_CONF_ORIGIN, confBackup);

            // Thread.sleep(111);

            if (confBackup.exists() && confBackup.isFile()) {
                log.info(confBackup.getAbsolutePath() + " => " + confBackup.exists());
                RESTORE_NEED = true;
            } else {
                log.error("nginx conf file backup not exist");
                adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "FAIL", "nginx conf file backup not exist");
                throw new Exception("nginx conf file backup not exist");
            }

            // Thread.sleep(111);

            final File newConfFile = new File(NGINX_CONF_DIR + "/" + NGINX_CONF_FILE);

            boolean createNewConfFile = newConfFile.createNewFile();

            log.info("createNewConfFile | " + createNewConfFile);

            // Thread.sleep(111);

            List<String> newConfFileText = NginxConfCreateUtil.CREATE_NEW_CONF_TEXT(policyEntityDto, nginxServerEntityList);

            FileUtils.writeLines(newConfFile, newConfFileText, System.lineSeparator());

            // Thread.sleep(111);

            log.info("new File Write Complete");

            try {
                NginxServiceControllUtil.NGINX_STOP();
            } catch (Exception e) {
            }

            // Thread.sleep(111);

            boolean nginxStartSuccess = NginxServiceControllUtil.NGINX_START();

            log.info("nginxStartSuccess | " + nginxStartSuccess);

            // Thread.sleep(111);

            if (nginxStartSuccess) {
                RESTORE_NEED = false;
            } else {
                log.info("nginx conf swap Fail");
                adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "FAIL", "nginx conf swap Fail");
                throw new Exception("nginx conf swap Fail");
            }

            // Thread.sleep(111);

            Set<String> domains = new HashSet<>();

            for (int i = 0; i < nginxServerEntityList.size(); ++i) {
                NginxServerEntityDto dto = nginxServerEntityList.get(i);
                String domain = dto.getDomainEntity().getDomain();
                domains.add(domain);
            }

            String ROOT_DOMAIN = policyEntityDto.getDomainEntity().getDomain();
            boolean certbotSuccess = NginxServiceControllUtil.CERTBOT_INIT(ROOT_DOMAIN, domains);
            log.info("certbotSuccess | " + certbotSuccess);
            if (!certbotSuccess) {
                log.info("CERTBOT Fail");
                adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "FAIL", "CERTBOT Fail");
                throw new Exception("CERTBOT Fail");
            }

            RESTORE_NEED = false;

            adminLogService.insertLog(AdminLogType.NGINX_UPDATE, "SUCCESS", "NGINX UPDATE END");

        } catch (Exception e) {
            log.error(e.getMessage() + " | " + e.getLocalizedMessage());

            if (RESTORE_NEED) {
                log.error(" ===== NGINX_BACKUP_START ===== ");
                try {
                    try {
                        NGINX_CONF_ORIGIN.delete();
                    } catch (Exception ex) {
                        log.error(e.getMessage());
                    }

                    FileUtils.copyFile(new File(OLD_CONF_BACKUP_FILE), NGINX_CONF_ORIGIN);

                    try {
                        NginxServiceControllUtil.NGINX_STOP();
                    } catch (Exception ex) {
                        log.error(e.getMessage());
                    }

                    boolean nginxRestoreStatus = NginxServiceControllUtil.NGINX_START();

                    log.error(" nginxRestoreStatus | " + nginxRestoreStatus);

                    adminLogService.insertLog(AdminLogType.NGINX_RESTORE, "SUCCESS", "NGINX RESTORE END");

                } catch (Exception ex) {
                    log.error(ex.getMessage());
                    adminLogService.insertLog(AdminLogType.NGINX_RESTORE, "FAIL", "NGINX RESTORE FAIL | " + ex.getMessage());
                }

            }

        } finally {

            log.info("\n" + "=======================================" + "\n" + "FINALIZE NGINX JOB !!!!!!" + "\n" + "=======================================");
        }

    }


}
