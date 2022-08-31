package kr.swim.nginx.controller;

import kr.swim.nginx.util.AuthTokenUtil;
import kr.swim.util.process.ProcessExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class PingController {

    @GetMapping("/ping")
    public Map<String, Object> ping() {
        Map<String, Object> map = new HashMap<>();
        map.put("ping", "pong");

        try {
            String token = AuthTokenUtil.GET_AUTH_TOKEN();
            log.error(" token create !!!  ==  " + token);

        } catch (IOException e) {
        }

        return map;
    }

}