package sw.im.swim.controller.view;

import kr.swim.util.system.SystemInfo;
import kr.swim.util.system.SystemInfoExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.date.DateFormatUtil;

import java.util.Date;

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
        GeneralConfig.SERVER_INFO = new SystemInfo();

        ModelAndView mav = new ModelAndView("dashboard/main");

        try {
            GeneralConfig.setCertDate();
            Date date1 = GeneralConfig.CERT_STARTED_AT.getTime();
            Date date2 = GeneralConfig.CERT_EXPIRED_AT.getTime();

            int leftDay = GeneralConfig.CERT_LEFT_DAY();

            mav.addObject("startDate", DateFormatUtil.DATE_FORMAT_yyyy_MM_dd.format(date1));
            mav.addObject("endDate", DateFormatUtil.DATE_FORMAT_yyyy_MM_dd.format(date2));
            mav.addObject("leftDay", leftDay);
        } catch (Exception e) {
        }

        mav.addObject("ip", GeneralConfig.CURRENT_IP);
        mav.addObject("serverInfo",   GeneralConfig.SERVER_INFO);
        mav.addObject("publicIpInfo", GeneralConfig.PUBLIC_IP_INFO);
        mav.addObject("dto", GeneralConfig.CURRENT_SERVER_INFO);

        return mav;
    }


}
