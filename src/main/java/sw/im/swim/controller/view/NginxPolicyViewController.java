package sw.im.swim.controller.view;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.service.NginxPolicyService;
import sw.im.swim.service.NginxServerService;
import sw.im.swim.service.NginxServerSubService;

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
        ModelAndView mav = new ModelAndView("nginxpolicy/form");

        HashSet<Long> linkedNginxServerSet = new HashSet<>();
        try {

            String nginxServerSidString = "";
            NginxPolicyEntityDto nginxPolicy = null;

            try {
                nginxPolicy = nginxPolicyService.get();
            } catch (Exception e) {
                mav.setViewName("nginxpolicy/error");
                mav.addObject("errorMsg", "정책 생성 할 수 없음.");
                return mav;
            }

            if (nginxPolicy == null) {
                mav.setViewName("nginxpolicy/error");
                mav.addObject("errorMsg", "정책 없음.");
            }

            mav.addObject("nginxPolicy", nginxPolicy);

            List<Long> linkedNginxServerList = nginxPolicyService.getNginxServers(nginxPolicy.getSid());
            for (int i = 0; i < linkedNginxServerList.size(); i++) {
                nginxServerSidString += linkedNginxServerList.get(i) + ",";
                linkedNginxServerSet.add(linkedNginxServerList.get(i));
            }
            mav.addObject("nginxServerSidString", nginxServerSidString);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        List<NginxServerEntityDto> nginxServerList = nginxServerService.getAll();
        for (int i = 0; i < nginxServerList.size(); i++) {
            Long tempSid = nginxServerList.get(i).getSid();
            log.error("tempSid = " + tempSid);
            nginxServerList.get(i).setSelected(linkedNginxServerSet.contains(tempSid));
        }
        mav.addObject("nginxServerList", nginxServerList);
        mav.addObject("nginxServerListSize", nginxServerList.size());


        List<DomainEntityDto> linkedNginxServerList = nginxServerSubService.getAllDomains();
        mav.addObject("domainList", linkedNginxServerList);

        return mav;
    }

}
