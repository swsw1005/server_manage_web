package sw.im.swim.worker.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.bean.util.DatabaseServerUtil;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.exception.FileTooSmallException;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.date.DateFormatUtil;
import sw.im.swim.worker.ProcessExecutor;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.Callable;

@Slf4j
@RequiredArgsConstructor
public class DbBackupWorker implements Callable<String> {

    private final AdminLogService adminLogService;
    private final DatabaseServerEntityDto databaseServerEntityDto;
    private final String DB_NAME;

    @Override
    public String call() throws Exception {

        final String ip = databaseServerEntityDto.getServerInfoEntity().getIp();
        final String port = String.valueOf(databaseServerEntityDto.getPort());
        final String id = databaseServerEntityDto.getId();
        final String password = databaseServerEntityDto.getPassword();
        final DbType dbType = databaseServerEntityDto.getDbType();

        final String timestamp = DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss_z.format(Calendar.getInstance(GeneralConfig.TIME_ZONE).getTime());

        final String fileName = DatabaseServerUtil.DB_DUMP_FILE_TMP + ip + "_" + port + "=" + DB_NAME + "=" + dbType.name() + "_" + timestamp + ".sql";

        try {

            final String ROOT_DB_NAME = DatabaseServerUtil.ROOT_DB_NAME(dbType);
            final String JDBC_DRIVER_NAME = DatabaseServerUtil.JDBC_DRIVER_NAME(dbType);
            final String CHECK_QUERY = DatabaseServerUtil.CHECK_QUERY(dbType);
            final String DB_LIST_COMMAND = DatabaseServerUtil.DB_LIST_COMMAND(ip, port, id, password, dbType);
            final String DUMP_COMMAND = DatabaseServerUtil.DUMP_COMMAND(ip, port, id, password, DB_NAME, fileName, dbType);

            if (dbType.equals(DbType.POSTGRESQL)) {
                String pgpassLine = ip + ":" + port + ":" + DB_NAME + ":" + id + ":" + password;
                DatabaseServerUtil.ADD_PG_PASS(pgpassLine);
            }

            log.debug("DUMP_COMMAND => " + DUMP_COMMAND);

            final String[] arr = {"sh", "-c", DUMP_COMMAND};

            final String processResult = ProcessExecutor.runSimpleCommand(arr, 100);

            log.debug("processResult => " + processResult);

            final long LOOP_END = System.currentTimeMillis() + 5000;
            final File DUMP_FILE = new File(fileName);

            try {
                Thread.sleep(4444);
            } catch (Exception e) {
            }

            if (DUMP_FILE.length() < 9999) {
                throw new FileTooSmallException("FILE_TOO_SMALL | " + fileName);
            }

            log.debug("DUMP_FILE => " + DUMP_FILE.getAbsolutePath());

            FileUtils.moveFileToDirectory(DUMP_FILE, new File(DatabaseServerUtil.RCLONE_DIR), true);

            adminLogService.insertLog(AdminLogType.DB_SUCCESS, "SUCCESS", ip + ":" + port + " " + DB_NAME + " " + dbType.name());

        } catch (FileTooSmallException e) {
            
        } catch (Exception e) {
            adminLogService.insertLog(AdminLogType.DB_SUCCESS, "ERROR", ip + ":" + port + " " + DB_NAME + " " + dbType.name() + " | " + e.getMessage());
            log.error(e.getMessage(), e);
        } finally {
            try {
                new File(fileName).delete();
            } catch (Exception e) {
            }
        }
        return null;
    }
}
