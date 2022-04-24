package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.NotiEntityDto;
import sw.im.swim.service.NotiService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/noti")
@RequiredArgsConstructor
public class NotiViewController {

    private final NotiService notiService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "알림 채널 관리");
        mav.addObject("mainPageUrl", "/noti/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("noti/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("noti/list");

        List<NotiEntityDto> list = notiService.getAll();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("noti/form");
        return mav;
    }


}
