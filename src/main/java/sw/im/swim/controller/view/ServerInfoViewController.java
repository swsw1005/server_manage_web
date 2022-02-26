package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.ServerInfoService;
import sw.im.swim.service.WebServerService;
import sw.im.swim.util.SSHUtils;
import sw.im.swim.util.server.ServerSSHUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerInfoViewController {

    private final ServerInfoService serverInfoService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "Server 관리");
        mav.addObject("mainPageUrl", "/server/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("server/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("server/list");

        List<ServerInfoEntityDto> list = serverInfoService.getAll();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("server/form");
        return mav;
    }

    @GetMapping("/detail/{sid}")
    public ModelAndView detail(
            @PathVariable(value = "sid") long sid,
            HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("server/detail");

        ServerInfoEntityDto dto = serverInfoService.getBySid(sid);
        dto.getIp();

        try {
            Set<String> set = ServerSSHUtils.banIPs(dto);
            mav.addObject("ips", set);
        } catch (Exception e) {
            mav.addObject("ips", null);
        }

        mav.addObject("token", GeneralConfig.ADMIN_SETTING.getFAIL2BAN_TOKEN());
        mav.addObject("dto", dto);

        return mav;
    }

}
