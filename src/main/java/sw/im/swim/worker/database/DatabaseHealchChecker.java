package sw.im.swim.worker.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.DatabaseServerService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DatabaseHealchChecker implements Runnable {

    private final AdminLogService adminLogService;

    private final DatabaseServerEntityDto dto;

    @Override
    public void run() {
        log.info("START!! ----------------------------------------");

        File pgpass = DatabaseServerUtil.PG_PASS_DELETE();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            DbType dbType = dto.getDbType();

            final String query = dbType.getCHECK_QUERY();

            final String url = dbType.getJDBC_URL();

            Class.forName(dbType.getJDBC_DRIVER_NAME());
            final String driverUrl = String.format(url, dto.getServerInfoEntity().getIp(), dto.getPort(), dbType.getROOT_DB_NAME());
            connection = DriverManager.getConnection(driverUrl, dto.getId(), dto.getPassword());
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String result = rs.getString(1);
                log.debug(" result => " + result);
            }

        } catch (Exception e) {
            adminLogService.insertLog(AdminLogType.DB_FAIL, "FAIL", e.getLocalizedMessage());
            log.error(e.getMessage(), e);
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
            }
            try {
                rs.close();
            } catch (Exception e) {
            }
        }

    }
}
