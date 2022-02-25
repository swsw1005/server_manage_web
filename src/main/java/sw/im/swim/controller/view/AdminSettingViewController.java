package sw.im.swim.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sw.im.swim.bean.CronVO;
import sw.im.swim.bean.dto.AdminSettingEntityDto;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.service.AdminSettingService;
import sw.im.swim.service.NginxPolicyService;
import sw.im.swim.service.NginxServerService;
import sw.im.swim.service.NginxServerSubService;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/adminsetting")
@RequiredArgsConstructor
public class AdminSettingViewController {

    private final AdminSettingService adminSettingService;

    @GetMapping("/home")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("common/main");

        mav.addObject("title", "관리자 설정");
        mav.addObject("mainPageUrl", "/adminsetting/main");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("adminsetting/main");
        return mav;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("adminsetting/form");

        AdminSettingEntityDto setting = adminSettingService.getSetting();

        ArrayList<CronVO> cronList = new ArrayList<>(GeneralConfig.CRON_EXPRESSION_LIST);

        mav.addObject("cronList", cronList);
        mav.addObject("adminSetting", setting);
        return mav;
    }

}
