package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.bean.entity.NginxPolicyServerEntity;
import sw.im.swim.service.NginxPolicyService;
import sw.im.swim.service.NginxServerService;
import sw.im.swim.service.NginxServerSubService;
import sw.im.swim.service.WebServerService;
import sw.im.swim.util.nginx.NginxServiceControllUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/nginxpolicy")
@RequiredArgsConstructor
public class NginxPolicyViewController {

    private final NginxPolicyService nginxPolicyService;

    private final NginxServerService nginxServerService;

    private final NginxServerSubService nginxServerSubService;


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
    public ModelAndView form(
            @RequestParam(name = "sid", required = false, defaultValue = "-1") final String policySid,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("nginxpolicy/form");

        List<NginxServerEntityDto> nginxServerList = nginxServerService.getAll();
        mav.addObject("nginxServerList", nginxServerList);

        String nginxServerSidString = "";
        NginxPolicyEntityDto nginxPolicy = null;
        boolean insert = true;
        try {
            nginxPolicy = nginxPolicyService.get(Long.parseLong(policySid));
            if (nginxPolicy.getDeletedAt() != null) {
                throw new Exception();
            }
            insert = false;

            mav.addObject("nginxPolicy", nginxPolicy);

            List<Long> linkedNginxServerList = nginxPolicyService.getNginxServers(Long.parseLong(policySid));
            for (int i = 0; i < linkedNginxServerList.size(); i++) {
                nginxServerSidString += linkedNginxServerList.get(i) + ",";
            }
            mav.addObject("nginxServerSidString", nginxServerSidString);

        } catch (Exception e) {
        }
        mav.addObject("insert", insert);
        List<DomainEntityDto> linkedNginxServerList = nginxServerSubService.getAllDomains();
        mav.addObject("domainList", linkedNginxServerList);

        return mav;
    }


}
