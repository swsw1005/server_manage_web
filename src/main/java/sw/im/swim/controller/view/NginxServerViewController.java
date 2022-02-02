package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.FaviconEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.service.NginxServerService;
import sw.im.swim.service.NginxServerSubService;
import sw.im.swim.service.WebServerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/nginxserver")
@RequiredArgsConstructor
public class NginxServerViewController {

    private final NginxServerService nginxServerService;

    private final WebServerService webServerService;

    private final NginxServerSubService nginxServerSubService;

    @GetMapping("/home")
    public ModelAndView nginxserver(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "nginx 서버 관리");
        mav.addObject("mainPageUrl", "/nginxserver/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxserver/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxserver/list");

        List<NginxServerEntityDto> list = nginxServerService.getAll();
        mav.addObject("list", list);

        return mav;
    }

    @GetMapping("/form")
    public ModelAndView form(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxserver/form");

        List<DomainEntityDto> domainList = nginxServerSubService.getAllDomains();
        List<FaviconEntityDto> faviconList = nginxServerSubService.getAllFavicons();
        List<WebServerEntityDto> webServerList = webServerService.getAll();

        mav.addObject("domainList", domainList);
        mav.addObject("faviconList", faviconList);
        mav.addObject("webServerList", webServerList);

        return mav;
    }


}
