package sw.im.swim.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

@Slf4j
public class InternetTestJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

//        try {
//            speedTestService.speedTest();
//        } catch (IllegalStateException e) {
//            log.error(e.getMessage() + "_____");
//        } catch (RuntimeException e) {
//            log.error(e.getMessage() + "_____");
//        } catch (Exception e) {
//            log.error(e.getMessage() + "_____", e);
//        }

        try {
            ThreadWorkerPoolContext.getInstance().INTERNET_TEST_QUEUE.add(1);
        } catch (Exception e) {
        }

    }


}
