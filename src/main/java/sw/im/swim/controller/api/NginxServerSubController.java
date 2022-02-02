package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.entity.DomainEntity;
import sw.im.swim.bean.entity.FaviconEntity;
import sw.im.swim.service.NginxServerSubService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NginxServerSubController {

    private final NginxServerSubService nginxServerSubService;

    @RequestMapping(value = "/domains", method = {RequestMethod.GET})
    public Map<String, Object> domains(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<DomainEntityDto> list = nginxServerSubService.getAllDomains();
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

    @RequestMapping(value = "/favicons", method = {RequestMethod.GET})
    public Map<String, Object> favicons(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<FaviconEntityDto> list = nginxServerSubService.getAllFavicons();
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


    @RequestMapping(value = "/domain", method = {RequestMethod.POST})
    public Map<String, Object> insertDomain(
            @RequestParam(name = "domain", required = false, defaultValue = "") final String domain,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            DomainEntity entity = nginxServerSubService.insertDomain(domain);
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

    @RequestMapping(value = "/favicon", method = {RequestMethod.POST})
    public Map<String, Object> insertFavicon(
            @RequestParam(name = "path", required = false, defaultValue = "") final String path,
            @RequestParam(name = "description", required = false, defaultValue = "") final String description,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            FaviconEntity entity = nginxServerSubService.insertFavicon(path, description);
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

    @RequestMapping(value = "/domain", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteDomain(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            nginxServerSubService.deleteDomain(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/favicon", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteFavicon(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            nginxServerSubService.deleteFavicon(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
