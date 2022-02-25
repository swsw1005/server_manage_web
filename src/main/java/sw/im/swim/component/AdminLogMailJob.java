package sw.im.swim.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

@Slf4j
public class AdminLogMailJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            ThreadWorkerPoolContext.getInstance().ADMIN_LOG_MAIL_QUEUE.add(1);
        } catch (Exception e) {
        }

    }


}
