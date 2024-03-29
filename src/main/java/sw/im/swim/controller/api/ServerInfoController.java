package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.service.ServerInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ServerInfoController {

    private final ServerInfoService serverInfoService;

    @RequestMapping(value = "/server", method = {RequestMethod.GET})
    public Map<String, Object> server(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<ServerInfoEntityDto> list = serverInfoService.getAll();
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

    @RequestMapping(value = "/server", method = {RequestMethod.POST})
    public Map<String, Object> insertServer(
            @RequestParam(name = "name", required = false, defaultValue = "") final String name,
            @RequestParam(name = "id", required = false, defaultValue = "") final String id,
            @RequestParam(name = "ip", required = false, defaultValue = "") final String ip,
            @RequestParam(name = "password", required = false, defaultValue = "") final String password,
            @RequestParam(name = "sshPort", required = false, defaultValue = "") final int sshPort,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            ServerInfoEntityDto entity = serverInfoService.insertNew(name, id, password, ip, sshPort);
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


    @RequestMapping(value = "/server", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteServer(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            serverInfoService.delete(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
