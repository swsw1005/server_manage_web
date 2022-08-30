package sw.im.swim.component;

import kr.swim.util.system.PublicIpInfo;
import kr.swim.util.system.SystemInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.*;
import sw.im.swim.util.dns.GoogleDNSUtil;
import sw.im.swim.util.server.PublicIpInfoUtil;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.database.DatabaseBackupProducer;
import sw.im.swim.worker.noti.AdminLogEmailWorker;
import sw.im.swim.worker.speedtest.SpeedTestWorker;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class FixedCronJob {

    private final NginxServerSubService nginxServerSubService;

    private final AdminLogService adminLogService;

    private final DatabaseServerService databaseServerService;

    private final NotiService notiService;

    private final SpeedTestService speedTestService;

    private final ServerInfoService serverInfoService;

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

    }

    @Scheduled(cron = "0/10 * * * * *")
    public void dynamicDomainCheck() {

        try {
            GoogleDNSUtil DNSUtil = GoogleDNSUtil.getInstance();

            String IP = PublicIpInfo.getPublicIp();
            IP.length();

            String currIp = GeneralConfig.CURRENT_IP;

            log.debug("?? IP CHANGE ?? :: " + currIp + "  =>  " + IP);

            GeneralConfig.CURRENT_IP = IP;

            if (currIp.length() > 4 && currIp.equals(IP) == false) {

                log.warn("!! IP CHANGE !! :: " + currIp + "  =>  " + IP);

                try {
                    GeneralConfig.PUBLIC_IP_INFO = PublicIpInfoUtil.GET_PUBLIC_IP();
                } catch (Exception e) {
                    log.error(e + "  " + e.getMessage(), e);
                }

                if (GeneralConfig.ADMIN_SETTING.isDNS_UPDATE()) {
                    final String ROOT_DOMAIN = GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN();

                    DNSUtil.updateIp(ROOT_DOMAIN);
                    adminLogService.insertLog(AdminLogType.DNS, "SUCCESS", " IP CHANGE [" + currIp + "] > [" + IP + "]");
                }

            }

        } catch (Exception e) {
            adminLogService.insertLog(AdminLogType.DNS, "FAIL", e.getLocalizedMessage());
        }

    }


    @Scheduled(cron = "0/5 * * * * *")
    public void databaseServerBackup() {
        ThreadWorkerPoolContext.getInstance().DB_SERVER_WORKER.execute(new DatabaseBackupProducer(adminLogService, databaseServerService));
        ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(new AdminLogEmailWorker(adminLogService));
        ThreadWorkerPoolContext.getInstance().DEFAULT_WORKER.execute(new SpeedTestWorker(speedTestService));

        /**
         * TODO  auto insert new admin
         *
         * 1. check certain file
         * 2. if file exist, read and delete file
         * 3. read id, password, name
         * 4. insert as new admin
         * 5. if exisy, update
         *
         */
    }


    @Scheduled(cron = "3 0/1 * * * *")
    public void notiDtoSync() {
        serverInfoService.sync();
    }

}
