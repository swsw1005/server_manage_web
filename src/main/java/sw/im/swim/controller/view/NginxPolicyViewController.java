package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.service.NginxPolicyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/nginxpolicy")
@RequiredArgsConstructor
public class NginxPolicyViewController {

    private final NginxPolicyService nginxPolicyService;

    @GetMapping("/home")
    public ModelAndView nginxpolicy(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "nginx 정책 관리");
        mav.addObject("mainPageUrl", "/nginxpolicy/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxpolicy/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxpolicy/list");

        List<NginxPolicyEntityDto> list = nginxPolicyService.getAll();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxpolicy/form");
        return mav;
    }


}
