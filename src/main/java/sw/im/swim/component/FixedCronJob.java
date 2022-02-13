package sw.im.swim.component;

import ch.qos.logback.classic.Logger;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.DatabaseServerService;
import sw.im.swim.service.NginxPolicyService;
import sw.im.swim.service.NginxServerSubService;
import sw.im.swim.util.dns.GoogleDNSUtil;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.database.DbServerWorker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                            fileSet.add(tempICO.getAbsolutePath());
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
                DNSUtil.updateIp(nginxPolicyService.getRootDomain());
                adminLogService.insertLog("DNS", "SUCCESS", " IP CHANGE [" + currIp + "] > [" + IP + "]");
            }

        } catch (Exception e) {
            adminLogService.insertLog("DNS", "FAIL", e.getLocalizedMessage());
        }
    }


    @Scheduled(cron = "0 0 0/3 * * *")
    public void databaseServerBackup() {
        try {
            Thread.sleep(1111);
        } catch (Exception e) {
        }

        try {
            new File(DatabaseServerUtil.DB_DUMP_FILE_TMP).mkdirs();
        } catch (Exception e) {
        }

        File pgpass = DatabaseServerUtil.PG_PASS_DELETE();

        try {
            List<DatabaseServerEntityDto> databaseServerList = databaseServerService.getAll();

            HashSet<String> pgpassList = new HashSet<>();
            // ip:port:database:id:password
            for (DatabaseServerEntityDto dto : databaseServerList) {
                final DbType dbType = dto.getDbType();

                if (dbType.equals(DbType.POSTGRESQL)) {
                    final String ip = dto.getIp();
                    final String port = String.valueOf(dto.getPort());
                    final String database = "postgres";
                    final String id = dto.getId();
                    final String password = dto.getPassword();
                    String pgpassLine = ip + ":" + port + ":" + database + ":" + id + ":" + password;
                    DatabaseServerUtil.ADD_PG_PASS(pgpassLine);
                }
            } // for end

            for (DatabaseServerEntityDto dto : databaseServerList) {
                ThreadWorkerPoolContext.getInstance().DB_SERVER_WORKER.execute(new DbServerWorker(adminLogService, dto));
            }

        } catch (Exception e) {
            adminLogService.insertLog("DB_BACKUP", "FAIL", e.getLocalizedMessage());
        }

    }


}
