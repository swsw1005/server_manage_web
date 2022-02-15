package sw.im.swim.worker.database;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.date.DateFormatUtil;
import sw.im.swim.util.process.ProcessExecUtil;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

@Slf4j
@RequiredArgsConstructor
public class DbServerWorker implements Runnable {

    private final AdminLogService adminLogService;

    private final DatabaseServerEntityDto databaseServerEntityDto;

    @Override
    public void run() {

        final String ip = databaseServerEntityDto.getIp();
        final String port = String.valueOf(databaseServerEntityDto.getPort());
        final String id = databaseServerEntityDto.getId();
        final String password = databaseServerEntityDto.getPassword();
        final DbType dbType = databaseServerEntityDto.getDbType();

        try {

            final String timestamp = DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss_z.format(Calendar.getInstance(GeneralConfig.TIME_ZONE).getTime());

            final String fileName = ip + "_" + port + "_" + dbType.name() + "_" + timestamp + ".sql";

            final String ROOT_DB_NAME = DatabaseServerUtil.ROOT_DB_NAME(dbType);
            final String JDBC_DRIVER_NAME = DatabaseServerUtil.JDBC_DRIVER_NAME(dbType);
            final String CHECK_QUERY = DatabaseServerUtil.CHECK_QUERY(dbType);
            final String DB_LIST_COMMAND = DatabaseServerUtil.DB_LIST_COMMAND(ip, port, id, password, dbType);

            final String[] arr = {"sh", "-c", DB_LIST_COMMAND};

            log.debug(DB_LIST_COMMAND);

            List<String> cliResult = ProcessExecUtil.RUN_READ_COMMAND_LIST(arr);

            HashSet<String> includeDatabases = new HashSet<>();
            HashSet<String> excludeDatabases = new HashSet<>();

            Set<String> excludes = DatabaseServerUtil.DB_LIST_EXCLUDE(dbType);

            for (int i = 0; i < cliResult.size(); i++) {
                try {
                    String tempDbName = cliResult.get(i).trim().split("\\|")[0].trim();
                    boolean excluded = false;
                    for (String exclude : excludes) {
                        if (exclude.equalsIgnoreCase(tempDbName)) {
                            excludeDatabases.add(tempDbName);
                            excluded = true;
                            break;
                        }
                    }
                    if (excluded == false) {
                        includeDatabases.add(tempDbName);
                    }
                } catch (Exception e) {
                }
            } // for i end

            Gson gson = new Gson();
            log.warn("includeDatabases  " + gson.toJson(includeDatabases) + "\t" + "excludeDatabases  " + gson.toJson(excludeDatabases));

            if (excludeDatabases.size() < (excludes.size() - 2)) {
                throw new Exception("DB_LIST FAIL | " + ip + ":" + port + " | " + dbType.name());
            }

            for (String DB_NAME : includeDatabases) {
                DbBackupWorker worker = new DbBackupWorker(adminLogService, databaseServerEntityDto, DB_NAME);
                ThreadWorkerPoolContext.getInstance().DB_DUMP_WORKER.submit(worker);
            }

            adminLogService.insertLog(AdminLogType.DB_LIST_UP, "SUCCESS", ip + ":" + port + " " + dbType.name());

        } catch (Exception e) {
            adminLogService.insertLog(AdminLogType.DB_LIST_UP, "ERROR", ip + ":" + port + " " + dbType.name() + " | " + e.getLocalizedMessage());
            log.error(e.getMessage());
        }


    }
}
