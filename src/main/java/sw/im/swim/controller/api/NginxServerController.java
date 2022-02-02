package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.entity.NginxServerEntity;
import sw.im.swim.service.NginxServerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NginxServerController {

    private final NginxServerService nginxServerService;

    @RequestMapping(value = "/nginxservers", method = {RequestMethod.GET})
    public Map<String, Object> nginxservers(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<NginxServerEntityDto> list = nginxServerService.getAll();
            map.put("list", list);
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return map;
    }

    @RequestMapping(value = "/nginxserver", method = {RequestMethod.POST})
    public Map<String, Object> insertNginxServer(
            @RequestParam(name = "name", required = false, defaultValue = "") final String name,
            @RequestParam(name = "seperateLog", required = false, defaultValue = "true") final boolean seperateLog,
            @RequestParam(name = "domainInfoSid", required = false, defaultValue = "") final String domainInfoSid,
            @RequestParam(name = "faviconInfoSid", required = false, defaultValue = "") final String faviconInfoSid,
            @RequestParam(name = "webServerInfoSid", required = false, defaultValue = "") final String webServerInfoSid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            NginxServerEntity entity = nginxServerService.insertNew(
                    name,
                    seperateLog,
                    Long.parseLong(domainInfoSid),
                    Long.parseLong(faviconInfoSid),
                    Long.parseLong(webServerInfoSid)
            );
            map.put("entity", entity);
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return map;
    }


    @RequestMapping(value = "/nginxserver", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteNginxServer(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            nginxServerService.delete(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
