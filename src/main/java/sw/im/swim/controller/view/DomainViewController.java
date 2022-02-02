package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.service.NginxServerSubService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/domain")
@RequiredArgsConstructor
public class DomainViewController {

    private final NginxServerSubService nginxServerSubService;

    @GetMapping("/home")
    public ModelAndView domain(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "domain 관리");
        mav.addObject("mainPageUrl", "/domain/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("domain/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("domain/list");

        List<DomainEntityDto> list = nginxServerSubService.getAllDomains();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("domain/form");
        return mav;
    }


}
