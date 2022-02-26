package sw.im.swim.worker.noti;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.AdminLogEntityDto;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.enums.AdminLogType;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.util.date.DateFormatUtil;
import sw.im.swim.util.email.EmailUtil;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AdminLogEmailWorker implements Runnable {

    private final AdminLogService adminLogService;

    @Override
    public void run() {

        int job = -1;
        try {
            job = ThreadWorkerPoolContext.getInstance().ADMIN_LOG_MAIL_QUEUE.poll();
        } catch (Exception e) {
        }

        if (job < 1) {
            return;
        }

        try {

//            log.error(" !!! ADMIN EMAIL SEND !!! ");

            List<AdminLogEntityDto> list = adminLogService.listAll();

            String content = buildMailContent(list);

            AdminSettingEntityDto setting = GeneralConfig.ADMIN_SETTING;

//            log.error("GeneralConfig.ADMIN_SETTING   " + new Gson().toJson(setting));

            String title = setting.getADMIN_LOG_MAIL_TITLE()
                    + "  "
                    + DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss_z.format(new Date());
            EmailUtil.sendEmail(
                    setting.getSMTP_USER(),
                    setting.getSMTP_PASSWORD(),
                    setting.getSMTP_HOST(),
                    setting.getSMTP_PORT(),
                    setting.isSMTP_STARTTLS_ENABLE(),
                    setting.isSMTP_AUTH(),
                    setting.isSMTP_SSL_ENABLE(),
                    setting.getSMTP_SSL_TRUST(),
                    setting.getADMIN_EMAIL(),
                    title,
                    content
            );
            Set<Long> ids = list.stream().map(AdminLogEntityDto::getSid).collect(Collectors.toSet());
            adminLogService.deleteByIds(ids);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            adminLogService.insertLog(AdminLogType.MAIL, "FAIL", e.getLocalizedMessage());
        } finally {

        }

        ThreadWorkerPoolContext.getInstance().ADMIN_LOG_MAIL_QUEUE.clear();

    }


    private String buildMailContent(List<AdminLogEntityDto> list) {

        String content = "<table border='1'>";

        if (list == null || list.size() == 0) {
            content += "<tr><th><h3>";
            content += "로그가 없습니다.";
            content += "</h3></th></tr>";
        } else {

            for (int i = 0; i < list.size(); i++) {

                AdminLogEntityDto dto = list.get(i);

                String getCreated = dto.getCreated();
                String getMessage1 = dto.getMessage1();
                String getMessage2 = dto.getMessage2();
                String getTitle = dto.getTitle().name();

                content += "<tr>";
                content += "<td>";
                content += getCreated;
                content += "</td>";
                content += "<td>";
                content += getTitle;
                content += "</td>";
                content += "<td>";
                content += getMessage1;
                content += "</td>";
                content += "</tr>";

                content += "<tr>";
                content += "<td colspan='3'>";
                content += getMessage2;
                content += "</td>";
                content += "</tr>";
            }


        }
        content += "</table>";

        return content;
    }


}
