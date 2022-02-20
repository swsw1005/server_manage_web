package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.service.AdminSettingService;
import sw.im.swim.service.NginxPolicyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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


}
