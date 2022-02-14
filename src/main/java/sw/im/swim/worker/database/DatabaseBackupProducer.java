package sw.im.swim.worker.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.DatabaseServerService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import java.io.File;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DatabaseBackupProducer implements Runnable {

    private final AdminLogService adminLogService;

    private final DatabaseServerService databaseServerService;

    @Override
    public void run() {
        try {
            new File(DatabaseServerUtil.DB_DUMP_FILE_TMP).mkdirs();
        } catch (Exception e) {
        }

        int job = -1;
        try {
            job = ThreadWorkerPoolContext.getInstance().DB_SERVER_QUEUE.poll();
        } catch (Exception e) {
        }

        if (job > 0) {
            ThreadWorkerPoolContext.getInstance().DB_SERVER_QUEUE.clear();
        } else {
            return;
        }

        log.info("START!! ----------------------------------------");

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
