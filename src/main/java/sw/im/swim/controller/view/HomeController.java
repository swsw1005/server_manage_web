package sw.im.swim.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {


    @RequestMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            HttpServletRequest request
    ) {

        ModelAndView mav = new ModelAndView(new RedirectView("/login/logout"));
        return mav;
    }


    @RequestMapping("/login/requiredNotify")
    public ModelAndView loginRequiredNotify(
            HttpServletRequest request
    ) {

        ModelAndView mav = new ModelAndView(new RedirectView("/login/requiredNotify"));
        return mav;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {


        ModelAndView mav = new ModelAndView(new RedirectView("/login/logout"));
        return mav;
    }


}
