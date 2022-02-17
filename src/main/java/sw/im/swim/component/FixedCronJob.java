package sw.im.swim.component;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.DatabaseServerService;
import sw.im.swim.service.NginxPolicyService;
import sw.im.swim.service.NginxServerSubService;
import sw.im.swim.util.dns.GoogleDNSUtil;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.database.DatabaseBackupProducer;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixedCronJob {

    private final NginxServerSubService nginxServerSubService;

    private final NginxPolicyService nginxPolicyService;

    private final AdminLogService adminLogService;

    private final DatabaseServerService databaseServerService;

    @Scheduled(cron = "0/15 * * * * *")
    public void faviconAutoRegister() {
        try {

            Set<String> rootDirSet = new HashSet<>();
            Set<String> fileSet = new HashSet<>();

            List<FaviconEntityDto> list = nginxServerSubService.getAllFavicons();

            for (FaviconEntityDto dto : list) {

                try {
                    File tempFile = new File(dto.getPath()).getParentFile();
                    rootDirSet.add(tempFile.getAbsolutePath());
                } catch (Exception e) {
                }
            }

            for (String dir : rootDirSet) {

                try {
                    File tempFile = new File(dir);

                    File[] files = tempFile.listFiles();

                    for (int i = 0; i < files.length; i++) {
                        File tempICO = files[i];
                        if (tempICO.getAbsolutePath().endsWith(".ico")) {
                            String tempPath = tempICO.getAbsolutePath();

                            tempPath = tempPath.replace("C:", "");
                            tempPath = tempPath.replace("D:", "");
                            tempPath = tempPath.replace("E:", "");
                            tempPath = tempPath.replace("\\", "/");
                            tempPath = tempPath.replace("\\\\", "/");

                            fileSet.add(tempPath);
                        }
                    }

                } catch (Exception e) {
                }

            } // for end

            for (FaviconEntityDto dto : list) {
                fileSet.remove(dto.getPath());
            }

            if (fileSet.size() > 0) {
                log.info(" NEW file size => " + fileSet.size());
            }

            for (String fileName : fileSet) {
                nginxServerSubService.insertSingle(fileName);
            }

        } catch (Exception e) {
        }

    }


    @Scheduled(cron = "0/5 * * * * *")
    public void dynamicDomainCheck() {
        try {
            GoogleDNSUtil DNSUtil = GoogleDNSUtil.getInstance();

            String IP = DNSUtil.GET_IP();
            IP.length();

            String currIp = GeneralConfig.CURRENT_IP;

            log.info("?? IP CHANGE ?? :: " + currIp + "  =>  " + IP);

            if (currIp.equals(IP) == false) {
                GeneralConfig.CURRENT_IP = IP;

                final String ROOT_DOMAIN = nginxPolicyService.getRootDomain();

                DNSUtil.updateIp(ROOT_DOMAIN);
                adminLogService.insertLog(AdminLogType.DNS, "SUCCESS", " IP CHANGE [" + currIp + "] > [" + IP + "]");
            }

        } catch (Exception e) {
            adminLogService.insertLog(AdminLogType.DNS, "FAIL", e.getLocalizedMessage());
        }
    }


    @Scheduled(cron = "0/5 * * * * *")
    public void databaseServerBackup() {
        ThreadWorkerPoolContext.getInstance().DB_SERVER_WORKER.execute(new DatabaseBackupProducer(adminLogService, databaseServerService));
    }


}
