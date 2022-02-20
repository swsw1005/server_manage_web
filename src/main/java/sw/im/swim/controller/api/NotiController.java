package sw.im.swim.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.service.NotiService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;

//    @RequestMapping(value = "/nginxpolicys", method = {RequestMethod.GET})
//    public Map<String, Object> nginxpolicys(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            List<NginxPolicyEntityDto> list = nginxPolicyService.getAll();
//            map.put("list", list);
//            map.put("code", 0);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            map.put("code", -1);
//            map.put("error_msg", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//        return map;
//    }

//    @RequestMapping(value = "/nginxpolicy/{sid}", method = {RequestMethod.GET})
//    public Map<String, Object> getNginxpolicy(
//            @PathVariable(name = "sid") final String sidStr,
//            HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            NginxPolicyEntityDto dto = nginxPolicyService.getAll(Long.parseLong(sidStr));
//            map.put("entity", dto);
//            map.put("code", 0);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            map.put("code", -1);
//            map.put("error_msg", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//        return map;
//    }

    @RequestMapping(value = "/noti", method = {RequestMethod.POST})
    public Map<String, Object> insert(
            @ModelAttribute NotiEntityDto dto1,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            NotiEntityDto dto = notiService.insertNew(
                    dto1.getName(),
                    dto1.getColumn1(),
                    dto1.getColumn2(),
                    dto1.getNotiType()
            );
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

//    @RequestMapping(value = "/noti", method = {RequestMethod.POST})
//    public Map<String, Object> update(
//            @RequestParam(name = "name", required = false, defaultValue = "") final String name,
//            @RequestParam(name = "workerConnections", required = false, defaultValue = "") final String workerConnections,
//            @RequestParam(name = "workerProcessed", required = false, defaultValue = "") final String workerProcessed,
//            @RequestParam(name = "nginxServerSidString", required = false, defaultValue = "") final String nginxServerSidString,
//            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
//            HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            NginxPolicyEntityDto dto = notiService.update(name,
//                    Integer.parseInt(workerProcessed),
//                    Integer.parseInt(workerConnections),
//                    nginxServerSidString,
//                    Long.parseLong(sid)
//            );
//            map.put("entity", dto);
//            map.put("code", 0);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            map.put("code", -1);
//            map.put("error_msg", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//        return map;
//    }


    @RequestMapping(value = "/notiToggle", method = {RequestMethod.POST})
    public Map<String, Object> notiToggle(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            notiService.toggle(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return map;
    }


//    @RequestMapping(value = "/nginxpolicy/add", method = {RequestMethod.POST})
//    public Map<String, Object> updateNginxPolicyAdd(
//            @RequestParam(name = "policySid", required = false, defaultValue = "") final String policySid,
//            @RequestParam(name = "nginxServerSid", required = false, defaultValue = "") final String nginxServerSid,
//            HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            nginxPolicyService.addNginxPolicy(Long.parseLong(policySid), Long.parseLong(nginxServerSid), true);
//            map.put("code", 0);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            map.put("code", -1);
//            map.put("error_msg", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//        return map;
//    }
//
//    @RequestMapping(value = "/nginxpolicy/remove", method = {RequestMethod.POST})
//    public Map<String, Object> updateNginxPolicyRemove(
//            @RequestParam(name = "policySid", required = false, defaultValue = "") final String policySid,
//            @RequestParam(name = "nginxServerSid", required = false, defaultValue = "") final String nginxServerSid,
//            HttpServletRequest request, HttpServletResponse response) {
//        Map<String, Object> map = new HashMap<>();
//        try {
//            nginxPolicyService.addNginxPolicy(Long.parseLong(policySid), Long.parseLong(nginxServerSid), false);
//            map.put("code", 0);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            map.put("code", -1);
//            map.put("error_msg", e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//        return map;
//    }


    @RequestMapping(value = "/noti", method = {RequestMethod.DELETE})
    public Map<String, Object> deleteNoti(
            @RequestParam(name = "sid", required = false, defaultValue = "") final String sid,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            notiService.delete(Long.parseLong(sid));
            map.put("code", 0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            map.put("code", -1);
            map.put("error_msg", e.getMessage());
        }
        return map;
    }


}
