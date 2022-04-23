package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.service.AdminSettingService;
import sw.im.swim.worker.context.ThreadWorkerPoolContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminSettingController {

    private final AdminSettingService adminSettingService;

    @RequestMapping(value = "/adminsetting/update", method = {RequestMethod.POST})
    public Map<String, Object> adminSettingUpdate(
            @ModelAttribute AdminSettingEntityDto dto,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            adminSettingService.update(dto);
            map.put("entity", dto);
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return map;
    }


    @RequestMapping(value = "/adminsetting/{job}", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> adminSettingSubmit(
            @PathVariable(name = "job") String jobType
    ) {
        Map<String, Object> map = new HashMap<>();
        try {

            jobType = jobType.trim().toLowerCase();

            if (jobType.contains("speedtest")) {
                ThreadWorkerPoolContext.getInstance().INTERNET_TEST_QUEUE.add(1);
            }
            if (jobType.contains("mail")) {
                ThreadWorkerPoolContext.getInstance().ADMIN_LOG_MAIL_QUEUE.add(1);
            }
            if (jobType.contains("dbbackup")) {
                ThreadWorkerPoolContext.getInstance().DB_SERVER_QUEUE.add(1);
            }

            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
        }
        return map;
    }


}
