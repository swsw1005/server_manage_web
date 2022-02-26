package sw.im.swim.controller.api;


import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.exception.TokenException;
import sw.im.swim.service.Fail2banService;
import sw.im.swim.service.ServerInfoService;
import sw.im.swim.util.server.ServerSSHUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class Fail2banController {

    private final Fail2banService fail2banService;

    private final ServerInfoService serverInfoService;

    // @RequestMapping("/view/main")
    // public ModelAndView main() {
    //     ModelAndView mav = new ModelAndView("fail2ban/main");

    //     return mav;
    // }

    @PostMapping("/fail2ban/block")
    public @ResponseBody
    Map<String, Object> block(
            @RequestParam(name = "job", defaultValue = "", required = false) String job,
            @RequestParam(name = "sid", defaultValue = "", required = false) long sid,
            @RequestParam(name = "ip", defaultValue = "", required = false) String ip,
            @RequestParam(name = "token", defaultValue = "", required = false) String token
    ) {
        Map<String, Object> map = new HashMap<>();
        try {

            if (token.equals(GeneralConfig.ADMIN_SETTING.getFAIL2BAN_TOKEN())) {

            } else {
                throw new Exception("TOKEN NOT MATCH");
            }

            ServerInfoEntityDto serverInfo = serverInfoService.getBySid(sid);
            serverInfo.getIp();

            switch (job) {
                case "ban":
                    ServerSSHUtils.banIP(serverInfo, ip);
                    break;
                case "unban":
                    ServerSSHUtils.unbanIP(serverInfo, ip);
                    break;
                default:
                    throw new Exception("job type not match");
            }
            map.put("msg", "success");
        } catch (Exception e) {
            map.put("msg", "fail | " + e.getLocalizedMessage());
        } finally {
            try {
            } catch (Exception e) {
            }
        }

        return map;
    }

    @PostMapping("/fail2ban/start")
    public @ResponseBody
    Map<String, Object> start(HttpServletRequest request) {
        log.warn("/api/start");
        return HANDLE(request, "start");
    }

    @PostMapping("/fail2ban/stop")
    public @ResponseBody
    Map<String, Object> stop(HttpServletRequest request) {
        log.warn("/api/stop");
        return HANDLE(request, "stop");
    }

    @PostMapping("/fail2ban/ban")
    public @ResponseBody
    Map<String, Object> ban(HttpServletRequest request) {
        log.warn("/api/ban");
        return HANDLE(request, "ban");
    }

    @PostMapping("/fail2ban/unban")
    public @ResponseBody
    Map<String, Object> unban(HttpServletRequest request) {
        log.warn("/api/unban");
        return HANDLE(request, "unban");
    }

    private Map<String, Object> HANDLE(HttpServletRequest request, String job) {
        Map<String, Object> map = new HashMap<>();
        String ip = GET_REQUEST_PARAM(request, "ip");
        String token = GET_REQUEST_PARAM(request, "token");
        String country = GET_REQUEST_PARAM(request, "country");
        String server = GET_REQUEST_PARAM(request, "server");

        try {
            if (country.length() > 10) {
                country = "UNDEFIENED";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("\n\n\n");
        log.info("server   \t+" + server);
        log.info("IP      \t+" + ip);
        log.info("country \t+" + country);
        log.info("token   \t+" + token);

        try {
            if (!token.equals(GeneralConfig.ADMIN_SETTING.getFAIL2BAN_TOKEN())) {
                throw new TokenException("TOKEN_VALID");
            }
            int result = fail2banService.SEND(server, ip, country, job);
            map.put("code", result);
        } catch (TokenException e) {
            map.put("code", e.CODE);
        } catch (Exception e) {
            map.put("code", -99);
            e.printStackTrace();
        }
        log.info("map==" + new Gson().toJson(map));
        return map;
    }

    private static String GET_REQUEST_PARAM(HttpServletRequest request, String key) {
        String output = "";
        try {
            output = request.getParameter(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return output;
    }

}

