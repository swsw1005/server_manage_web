package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.AdminLogEntityDto;
import sw.im.swim.bean.dto.SpeedTestResultDto;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.SpeedTestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/speedtest")
@RequiredArgsConstructor
public class SpeedTestViewController {

    private final SpeedTestService speedTestService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "인터넷속도");
        mav.addObject("mainPageUrl", "/speedtest/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("speedtest/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request, SpeedTestResultDto dto) {
        ModelAndView mav = new ModelAndView("speedtest/list");

        List<SpeedTestResultDto> list = speedTestService.getList(dto, 0, 100);
        mav.addObject("list", list);

        return mav;
    }

}
