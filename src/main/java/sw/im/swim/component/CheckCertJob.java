package sw.im.swim.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.NginxPolicyService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;
import sw.im.swim.worker.noti.NotiProducer;

@Slf4j
public class CheckCertJob extends QuartzJobBean {

    private NginxPolicyService nginxPolicyService;

    @Autowired
    public void setNginxPolicyService(NginxPolicyService nginxPolicyService) {
        this.nginxPolicyService = nginxPolicyService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        nginxPolicyService.ADJUST_NGINX_POLICY();

        try {
            final int leftDay = GeneralConfig.CERT_LEFT_DAY();

            final int certExpireNoti = GeneralConfig.ADMIN_SETTING.getCERT_EXPIRE_NOTI();

            final boolean needNoti = certExpireNoti >= leftDay;

            log.debug(" cert 남은 날짜 : " + leftDay + "  => " + certExpireNoti + " 일 전부터 알림 보낸다.  => " + needNoti);

            final String domain = GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN();

            if (needNoti) {
                String msg = "[" + domain + "] 인증서 만료 [" + leftDay + "] 일전 :: ";
                NotiProducer notiProducer = new NotiProducer(msg, AdminLogType.CERTBOT);
                ThreadWorkerPoolContext.getInstance().NOTI_WORKER.execute(notiProducer);
            }
        } catch (RuntimeException e) {
            log.error(e + "  " + e.getMessage(), e);
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }

    }


}
