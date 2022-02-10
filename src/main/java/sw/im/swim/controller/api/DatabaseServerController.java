package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.im.swim.bean.dto.DatabaseServerEntityDto;
import sw.im.swim.bean.entity.DatabaseServerEntity;
import sw.im.swim.bean.enums.DbType;
import sw.im.swim.service.DatabaseServerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DatabaseServerController {

    private final DatabaseServerService databaseServerService;

    @RequestMapping(value = "/database", method = {RequestMethod.GET})
    public Map<String, Object> database(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<DatabaseServerEntityDto> list = databaseServerService.getAll();
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

    @RequestMapping(value = "/database", method = {RequestMethod.POST})
    public Map<String, Object> insertDatabase(
            @RequestParam(name = "name", required = false, defaultValue = "") final String name,
            @RequestParam(name = "ip", required = false, defaultValue = "") final String ip,
            @RequestParam(name = "port", required = false, defaultValue = "") final Integer port,
            @RequestParam(name = "db_id", required = false, defaultValue = "") final String id,
            @RequestParam(name = "db_pw", required = false, defaultValue = "") final String password,
            @RequestParam(name = "dbType", required = false, defaultValue = "") final DbType dbType,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            DatabaseServerEntityDto entity = databaseServerService.insertNew(name, ip, port, id, password, dbType);
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


    @RequestMapping(value = "/database", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteDatabase(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            databaseServerService.delete(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
