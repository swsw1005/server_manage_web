package sw.im.swim.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sw.im.swim.bean.enums.DatabaseServerUtil;
import sw.im.swim.component.DatabaseBackupJob;
import sw.im.swim.repository.SettingEntityRepository;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.date.DateFormatUtil;
import sw.im.swim.util.dns.GoogleDNSUtil;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Getter
@Setter
@Slf4j
@Component
@RequiredArgsConstructor
public class PostConstruct {

    private static final String DEFAULT_CRON = "0 0/3 * * * ?";

    @Value("${google.dns.username}")
    private String GOOGLE_DNS_USER_NAME;

    @Value("${google.dns.password}")
    private String GOOGLE_DNS_PASSWORD;

    @Value(value = "${DB_BACKUP_CRON}")
    private String DB_BACKUP_CRON = "0 0 0/3 * * *";

    private final AdminLogService adminLogService;

    private final SettingEntityRepository settingEntityRepository;

//    USER_NAME=VUEKfrgtRxYM02Uh
//            PASSWORD=HKLSuJ41rME4DwRu

    @javax.annotation.PostConstruct
    public void INIT() throws SchedulerException {

        String ip = GoogleDNSUtil.getInstance().GET_IP();

        adminLogService.insertLog("START", "IP", ip);

        GeneralConfig.GOOGLE_DNS_USER_NAME = GOOGLE_DNS_USER_NAME;
        GeneralConfig.GOOGLE_DNS_PASSWORD = GOOGLE_DNS_PASSWORD;
        GeneralConfig.CURRENT_IP = ip;

        try {
            new File(DatabaseServerUtil.DB_DUMP_FILE_TMP).delete();
        } catch (Exception e) {
        }

        String cronExp = String.valueOf(DB_BACKUP_CRON);
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            CronScheduleBuilder cronSchedule = null;
            try {
                log.info("try cron => " + cronExp);
                cronSchedule = cronSchedule(cronExp);
            } catch (Exception e2) {
                cronExp = DEFAULT_CRON;
                log.info("retry cron => " + cronExp);
                cronSchedule = cronSchedule(DEFAULT_CRON);
            }

            JobDetail jobDetail = newJob(DatabaseBackupJob.class).withIdentity("jobName", "DATABASE_BACKUP_JOB").build();

            Trigger trigger = newTrigger().withIdentity("triggerName", "DATABASE_BACKUP_TRIGGER").withSchedule(cronSchedule).build();

            if (defaultScheduler.checkExists(jobDetail.getKey())) {
                defaultScheduler.deleteJob(jobDetail.getKey());
            }

            Date time = defaultScheduler.scheduleJob(jobDetail, trigger);

            log.warn("NEXT JOB TIME => " + DateFormatUtil.DATE_FORMAT_yyyyMMdd_T_HHmmssXXX.format(time));

            defaultScheduler.start();

        } catch (RuntimeException e) {
            log.error("Maybe Cron Expression ERROR.... [" + cronExp + "]");
        } catch (Exception e) {
            log.error(e.toString() + "\t" + e.getMessage() + " =====", e);
        }


    }

}
