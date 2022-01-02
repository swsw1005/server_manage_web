package sw.im.swim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {


    @GetMapping("")
    public ModelAndView home(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();

        mav.setViewName("test/home");

        return mav;


    }

}
