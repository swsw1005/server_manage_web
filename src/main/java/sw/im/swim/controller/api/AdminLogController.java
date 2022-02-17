package sw.im.swim.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.AdminLogEntityDto;
import sw.im.swim.service.AdminLogService;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminLogController {

    private final AdminLogService adminLogService;

    /**
     * <PRE>
     * 관리자 로그를 이메일로 전송하고
     * (옵션) 로그를 삭제한다.
     * </PRE>
     *
     * @param deleteSendLog
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/adminlog/sendEmail", method = {RequestMethod.POST})
    public Map<String, Object> sendEmail(
            @RequestParam(name = "deleteSendLog", required = false, defaultValue = "") final boolean deleteSendLog,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<AdminLogEntityDto> list = adminLogService.listAll();
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
