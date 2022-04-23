package sw.im.swim.worker.speedtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.SpeedTestService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
@RequiredArgsConstructor
public class SpeedTestWorker implements Runnable {

    private final SpeedTestService speedTestService;

    @Override
    public void run() {
        log.debug("SpeedTestWorker try...");

        int job = -1;
        try {
            job = ThreadWorkerPoolContext.getInstance().INTERNET_TEST_QUEUE.poll();
        } catch (Exception e) {
        }
        if (job == -1) {
            return;
        }

        log.info("SpeedTestWorker start !! -----------");

        try {
            speedTestService.speedTest();
        } catch (IllegalStateException e) {
            log.error(e.getMessage() + "__");
        } catch (RuntimeException e) {
            log.error(e.getMessage() + "___");
        } catch (Exception e) {
            log.error(e.getMessage() + "____", e);
        }

        log.info("SpeedTestWorker end !! -----------");

    }
}
