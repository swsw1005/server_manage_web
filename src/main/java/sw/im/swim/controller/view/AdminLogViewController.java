package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.AdminLogEntityDto;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.service.AdminLogService;
import sw.im.swim.service.NginxServerSubService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/adminlog")
@RequiredArgsConstructor
public class AdminLogViewController {

    private final AdminLogService adminLogService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "관리자 로그 관리");
        mav.addObject("mainPageUrl", "/adminlog/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("adminlog/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("adminlog/list");

        List<AdminLogEntityDto> list = adminLogService.listAll();
        mav.addObject("list", list);

        return mav;
    }

}
