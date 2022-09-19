package sw.im.swim.worker.speedtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.service.SpeedTestService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

@Slf4j
@RequiredArgsConstructor
public class SpeedTestWorker implements Runnable {

    private final SpeedTestService speedTestService;

    private final Long speedTestServerSid;

    private final int speedTestServerId;

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
            speedTestService.speedTest(speedTestServerSid, speedTestServerId);
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
