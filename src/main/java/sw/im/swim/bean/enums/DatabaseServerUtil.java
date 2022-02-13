package sw.im.swim.bean.enums;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sw.im.swim.util.process.ProcessExecUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DatabaseServerUtil {

    public static final String PG_PASS_FILE = "/root/.pgpass";
    public static final String DB_DUMP_FILE_TMP = "/usr/local/DB_DUMP_FILE_TMP/";
    public static final String RCLONE_DIR = "/usr/local/tempFiles/";
    public static final Set<String> PG_PASS_SET = ConcurrentHashMap.newKeySet();

    public static final String[] PG_PASS_COMMAND = {"sh", "-c", PG_PASS_FILE};


    public static final File PG_PASS_DELETE() {
        File pgpass = new File(DatabaseServerUtil.PG_PASS_FILE);
        try {
//            pgpass.delete();
        } catch (Exception e) {
        }
        PG_PASS_SET.clear();

        File parants = new File(pgpass.getParent());
        try {
            parants.mkdirs();
        } catch (Exception e) {
        }
        try {
            pgpass.createNewFile();
            ProcessExecUtil.RUN_READ_COMMAND(PG_PASS_COMMAND);
        } catch (Exception e) {
        }
        return pgpass;
    }

    public static final String ROOT_DB_NAME(final DbType dbType) {
        return dbType.ROOT_DB_NAME;
    }

    public static final String JDBC_DRIVER_NAME(final DbType dbType) {
        return dbType.JDBC_DRIVER_NAME;
    }

    public static final String CHECK_QUERY(final DbType dbType) {
        return dbType.CHECK_QUERY;
    }

    public static final Set<String> DB_LIST_EXCLUDE(final DbType dbType) {
        Set<String> set = new HashSet<>();
        String[] arr = dbType.DB_LIST_EXCLUDE.split("\\|");
        for (int i = 0; i < arr.length; i++) {
            String exclude = arr[i].trim();
            if (exclude.length() > 0) {
                set.add(exclude);
            }
        }
        return set;
    }

    public static final String JDBC_URL(final String ip, final String port, final String dbName, final DbType dbType) throws Exception {
        try {
            String pattern = dbType.JDBC_URL;

            String result = String.format(pattern, ip, port, dbName);

            log.info(result);

            return result;
        } catch (Exception e) {
            throw new Exception("Database Connect URL FAIL");
        }
    }

    public static final String DUMP_COMMAND(final String ip, final String port, final String id, final String password, final String dbName, final String fileName, final DbType dbType) throws Exception {
        try {
            String pattern = dbType.DUMP_COMMAND;

            String result;

            switch (dbType) {
                case POSTGRESQL:
                    result = String.format(pattern, ip, port, id, dbName, fileName);
                    break;

                default:
                    result = String.format(pattern, ip, port, id, password, dbName, fileName);
                    break;
            }

            log.info(result);

            return result;
        } catch (Exception e) {
            throw new Exception("Database Dump Command FAIL");
        }
    }

    public static final String DB_LIST_COMMAND(final String ip, final String port, final String id, final String password, final DbType dbType) throws Exception {
        try {
            String pattern = dbType.DB_LIST_COMMAND;

            String result;

            switch (dbType) {
                case POSTGRESQL:
                    result = String.format(pattern, ip, port, id, dbType.ROOT_DB_NAME);
                    break;

                default:
                    result = String.format(pattern, ip, port, id, password, dbType.ROOT_DB_NAME);
                    break;
            }

            log.info(result);

            return result;
        } catch (Exception e) {
            throw new Exception("Database Dump Command FAIL");
        }
    }


    public static void ADD_PG_PASS(String pgpassLine) {
        PG_PASS_SET.add(pgpassLine);
        WRITE_PG_PASS();
    }

    public static void WRITE_PG_PASS() {
        try {
            FileUtils.writeLines(new File(PG_PASS_FILE), "UTF-8", PG_PASS_SET, System.lineSeparator());
        } catch (Exception e) {
        }
    }


}
