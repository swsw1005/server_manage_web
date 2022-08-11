package sw.im.swim.controller.view;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sw.im.swim.bean.dto.AdminEntityDto;
import sw.im.swim.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AdminService adminService;

    @GetMapping("/")
    public ModelAndView main(HttpServletRequest request) {

        log.warn("/ comming...");

        final String contextPath = request.getContextPath();

        ModelAndView mav = new ModelAndView();
        RedirectView rv = new RedirectView(contextPath + "/login");
        try {
            HttpSession session = request.getSession();

            AdminEntityDto adminEntityDto = (AdminEntityDto) session.getAttribute("admin");

            if (adminEntityDto.getSid() > 0) {
                rv = new RedirectView(contextPath + "/nginxpolicy/home");
            }

        } catch (Exception e) {
        }

        log.warn(rv.getUrl());

        mav.setView(rv);

        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/login/main");
        return mav;
    }

    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> loginAjax(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            HttpServletRequest request, HttpServletResponse response
    ) {

        Map<String, Object> map = new HashMap<>();
        try {
            AdminEntityDto admin = adminService.login(email, password);
            admin.getName();
            request.getSession().setAttribute("admin", admin);

        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return map;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        ModelAndView mav = new ModelAndView(new RedirectView("/"));
        return mav;
    }

    @GetMapping("/login/test")
    public ModelAndView loginTest(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("test/test");
        return mav;
    }


}
