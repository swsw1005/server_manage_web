package kr.swim.nginx.controller;

import com.caffeine.lib.process.ProcessExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class NginxController {

    @PostMapping("/nginx/{job}")
    public Map<String, Object> nginxJob(@PathVariable(name = "job") String job, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("ping", "pong");

        log.warn(request.getMethod() + "  " + request.getRequestURI());
        try {
            NginxJob nginxJob = NginxJob.valueOf(job.toLowerCase().trim());
            log.warn("nginx job execute :: " + nginxJob.name() + " >> " + nginxJob.cmd);
            ProcessExecutor.runCommand(nginxJob.cmd);
        } catch (IllegalArgumentException e) {
            map.put("error", e.toString());
            map.put("error_message", e.getMessage());
            response.setStatus(409);
        } catch (NullPointerException e) {
            map.put("error", e.toString());
            map.put("error_message", e.getMessage());
            response.setStatus(409);
        } catch (Exception e) {
            map.put("error", e.toString());
            map.put("error_message", e.getMessage());
            response.setStatus(409);
        }
        return map;
    }


    public enum NginxJob {
        /**
         *
         */
        reload(new String[]{"sh", "-c", "service nginx reload"}),
        /**
         *
         */
        restart(new String[]{"sh", "-c", "service nginx restart"}),
        /**
         *
         */
        start(new String[]{"sh", "-c", "service nginx start"}),
        /**
         *
         */
        stop(new String[]{"sh", "-c", "service nginx stop"}),
        /**
         *
         */
        kill(new String[]{"sh", "-c", "pkill nginx"}),
        /**
         *
         */
        ;

        private String[] cmd;

        NginxJob(String[] cmd) {
            this.cmd = cmd;
        }
    }


}
