package sw.im.swim.controller.api;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.exception.TokenException;
import sw.im.swim.service.Fail2banService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/fail2ban")
public class Fail2banController {

    @Autowired
    private Fail2banService fail2banService;

    // @RequestMapping("/view/main")
    // public ModelAndView main() {
    //     ModelAndView mav = new ModelAndView("fail2ban/main");

    //     return mav;
    // }

    @RequestMapping("/api/block")
    public @ResponseBody
    Map<String, Object> block(

            @RequestParam(name = "job", defaultValue = "", required = false) String job,
            @RequestParam(name = "ip", defaultValue = "", required = false) String ip,
            @RequestParam(name = "token", defaultValue = "", required = false) String token

    ) {
        Map<String, Object> map = new HashMap<>();

        if (job == null) {
            job = "";
        }
        if (ip == null) {
            ip = "";
        }
        if (token == null) {
            token = "";
        }

        String command = "fail2ban-client set sshd unbanip ";

        Process p = null;
        map.put("success", 0);

        try {

            if (token.equals("dntjddla0772!@")) {
                map.put("success", 1);
            } else {
                throw new Exception();
            }

            String[] ipArr = ip.split("\\.");

            log.info("ip==" + ip);
            log.info(new Gson().toJson(ipArr));

            int[] ipArr_ = new int[4];
            map.put("success", 2);

            for (int i = 0; i < ipArr_.length; i++) {
                ipArr_[i] = Integer.parseInt(ipArr[i].trim());

                if (ipArr_[i] > 255 || ipArr_[i] < 0) {
                    throw new Exception();
                }
                command += ipArr_[i];
                command += ".";
            }
            command += ".";

            map.put("success", 3);

            command = command.replace("..", "");

            log.info("---------------\n" + command + "\n---------------------\n");

            p = Runtime.getRuntime().exec(command);

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = null;

            map.put("success", 4);

            int count = 0;

            String result = "";

            while ((line = br.readLine()) != null) {
                log.warn(line);
                result += line;
                result += "\n";
            }

            log.info("===================\n" + result + "\n=======================\n");

            map.put("success", 5);

        } catch (Exception e) {
            map.put("fail", -1);
        } finally {
            try {
                p.destroy();
            } catch (Exception e) {
            }
        }

        return map;
    }

    @PostMapping("/api/start")
    public @ResponseBody
    Map<String, Object> start(HttpServletRequest request) {
        log.warn("/api/start");
        return HANDLE(request, "start");
    }

    @PostMapping("/api/stop")
    public @ResponseBody
    Map<String, Object> stop(HttpServletRequest request) {
        log.warn("/api/stop");
        return HANDLE(request, "stop");
    }

    @PostMapping("/api/ban")
    public @ResponseBody
    Map<String, Object> ban(HttpServletRequest request) {
        log.warn("/api/ban");
        return HANDLE(request, "ban");
    }

    @PostMapping("/api/unban")
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
        }

        log.info("\n\n\n");
        log.info("server   \t+" + server);
        log.info("IP      \t+" + ip);
        log.info("country \t+" + country);
        log.info("token   \t+" + token);

        try {
            if (!token.equals(GeneralConfig.FAIL2BAN_TOKEN)) {
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
        }
        return output;
    }

}

