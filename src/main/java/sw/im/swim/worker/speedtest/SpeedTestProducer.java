package sw.im.swim.worker.speedtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.SpeedTestServerEntityDto;
import sw.im.swim.service.SpeedTestService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import java.util.List;

/**
 * <PRE>
 * 1. speedtest 서버 리스트 확인
 * (한국은 무조건, 국외는 일정 확률로 가져옴)
 * 2. 해당 리스트 SpeedTestWorker 통해 작업 시작
 * </PRE>
 */
@Slf4j
@RequiredArgsConstructor
public class SpeedTestProducer implements Runnable {

    private final SpeedTestService speedTestService;

    @Override
    public void run() {

        int job = -1;
        try {
            job = ThreadWorkerPoolContext.getInstance().INTERNET_TEST_QUEUE.poll();
        } catch (Exception e) {
        }
        if (job == -1) {
            return;
        }

        log.warn("speedtestProducer START !");

        try {

            List<SpeedTestServerEntityDto> list = speedTestService.getServerList();

            log.warn("speedtestProducer list.size ! " + list.size());

            for (SpeedTestServerEntityDto speedTestServerEntityDto : list) {
                Long sid = speedTestServerEntityDto.getSid();
                int serverId = speedTestServerEntityDto.getServerId();
                ThreadWorkerPoolContext.getInstance().SPEED_TEST_WORKER.execute(new SpeedTestWorker(speedTestService, sid, serverId));
            }

        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }

    }
}
