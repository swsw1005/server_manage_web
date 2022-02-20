package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.service.NginxServerSubService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/favicon")
@RequiredArgsConstructor
public class FaviconViewController {

    private final NginxServerSubService nginxServerSubService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "favicon 관리");
        mav.addObject("mainPageUrl", "/favicon/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("favicon/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("favicon/list");

        List<FaviconEntityDto> list = nginxServerSubService.getAllFavicons();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("favicon/form");
        return mav;
    }

}
