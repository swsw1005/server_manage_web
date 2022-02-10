package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.bean.entity.WebServerEntity;
import sw.im.swim.service.WebServerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WebServerController {

    private final WebServerService webServerService;

    @RequestMapping(value = "/webservers", method = {RequestMethod.GET})
    public Map<String, Object> webservers(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<WebServerEntityDto> list = webServerService.getAll();
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

    @RequestMapping(value = "/webserver", method = {RequestMethod.POST})
    public Map<String, Object> insertWebserver(
            @RequestParam(name = "name", required = false, defaultValue = "") final String name,
            @RequestParam(name = "https", required = false, defaultValue = "false") final boolean https,
            @RequestParam(name = "ip", required = false, defaultValue = "") final String ip,
            @RequestParam(name = "port", required = false, defaultValue = "") final Integer port,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            WebServerEntityDto entity = webServerService.insertNew(name, https, ip, port);
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


    @RequestMapping(value = "/webserver", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteWebserver(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            webServerService.delete(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
