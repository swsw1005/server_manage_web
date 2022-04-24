package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sw.im.swim.bean.dto.Fail2banLogEntityDto;
import sw.im.swim.bean.enums.JailType;
import sw.im.swim.bean.enums.JobType;
import sw.im.swim.service.Fail2banLogService;
import sw.im.swim.service.NotiService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Fail2banLogController {

    private final NotiService notiService;

    private final Fail2banLogService fail2banLogService;

    @RequestMapping(value = "/fail2ban/{jail}/{job}", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> fail2banLogInsert(
            @PathVariable(name = "jail") JailType jailType,
            @PathVariable(name = "job") JobType jobType,
            Fail2banLogEntityDto dto,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {

            log.error("jail : " + jailType + "  job : " + jobType);

            dto.setJailType(jailType);
            dto.setJobType(jobType);

            Fail2banLogEntityDto log = fail2banLogService.insertLog(dto);
            map.put("log", log);
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return map;
    }

}
