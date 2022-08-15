package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.server.ServerInfoUtil;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/dashboard")
public class DashBoardViewController {

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("common/main");
        mav.addObject("mainPageUrl", "/dashboard/main");
        return mav;
    }

    @RequestMapping(value = {"/main"}, method = RequestMethod.GET)
    public ModelAndView main() {
        ServerInfoUtil.ServerInfo serverInfo = GeneralConfig.SERVER_INFO;
        ServerInfoUtil.PublicIpInfo publicIpInfo = GeneralConfig.PUBLIC_IP_INFO;

        ModelAndView mav = new ModelAndView("dashboard/main");
        mav.addObject("ip", GeneralConfig.CURRENT_IP);
        mav.addObject("serverInfo", serverInfo);
        mav.addObject("publicIpInfo", publicIpInfo);
        mav.addObject("dto", GeneralConfig.CURRENT_SERVER_INFO);

        return mav;
    }


}
