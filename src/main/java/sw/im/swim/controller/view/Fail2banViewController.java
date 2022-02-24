package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.service.NginxServerSubService;
import sw.im.swim.service.WebServerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/fail2ban")
@RequiredArgsConstructor
public class Fail2banViewController {

    private final WebServerService webServerService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "서버 ssh 관리");
        mav.addObject("mainPageUrl", "/fail2ban/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("fail2ban/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("fail2ban/list");

        List<WebServerEntityDto> list = webServerService.getAll();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("fail2ban/form");
        return mav;
    }

}
