package sw.im.swim.component;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mchange.v2.lang.SystemUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.SystemPropertyUtils;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.*;
import sw.im.swim.util.dns.GoogleDNSUtil;
import sw.im.swim.util.server.ServerInfoUtil;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.database.DatabaseBackupProducer;
import sw.im.swim.worker.database.DatabaseHealchChecker;
import sw.im.swim.worker.noti.AdminLogEmailWorker;
import sw.im.swim.worker.speedtest.SpeedTestWorker;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixedCronJob {

    private final NginxServerSubService nginxServerSubService;

    private final NginxPolicyService nginxPolicyService;

    private final AdminLogService adminLogService;

    private final DatabaseServerService databaseServerService;

    private final NotiService notiService;

    private final SpeedTestService speedTestService;

    @Scheduled(cron = "17 0/1 * * * *")
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

        try {
            GeneralConfig.SERVER_INFO = ServerInfoUtil.getServerInfo();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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

            GeneralConfig.CURRENT_IP = IP;

            if (currIp.equals(IP) == false) {

                log.error("!! IP CHANGE !! :: " + currIp + "  =>  " + IP);

                if (GeneralConfig.ADMIN_SETTING.isDNS_UPDATE()) {
                    final String ROOT_DOMAIN = nginxPolicyService.getRootDomain();

                    DNSUtil.updateIp(ROOT_DOMAIN);
                    adminLogService.insertLog(AdminLogType.DNS, "SUCCESS", " IP CHANGE [" + currIp + "] > [" + IP + "]");
                }

            }

        } catch (Exception e) {
            adminLogService.insertLog(AdminLogType.DNS, "FAIL", e.getLocalizedMessage());
        }


        try {
            GeneralConfig.SERVER_INFO = ServerInfoUtil.getServerInfo();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


    }


    @Scheduled(cron = "0/5 * * * * *")
    public void databaseServerBackup() {

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
        }

        ThreadWorkerPoolContext.getInstance().DB_SERVER_WORKER.execute(new DatabaseBackupProducer(adminLogService, databaseServerService));
        ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(new AdminLogEmailWorker(adminLogService));
        ThreadWorkerPoolContext.getInstance().DEFAULT_WORKER.execute(new SpeedTestWorker(speedTestService));

    }


    @Scheduled(cron = "3 0/1 * * * *")
    public void notiDtoSync() {
        notiService.getAll();
    }

    @Scheduled(cron = "7 0/1 * * * *")
    public void serverHealthCheck() {
        try {
// TODO 서버 ssh 체크 개발

        } catch (Exception e) {
            log.error(e.getMessage() + "_____", e);
        }

    }


//    @Scheduled(cron = "0 0 0/1 * * *")
//    public void serverSpeedTest() {
//        try {
//            speedTestService.speedTest();
//        } catch (IllegalStateException e) {
//            log.error(e.getMessage() + "_____");
//        } catch (RuntimeException e) {
//            log.error(e.getMessage() + "_____");
//        } catch (Exception e) {
//            log.error(e.getMessage() + "_____", e);
//        }
//
//    }

}
