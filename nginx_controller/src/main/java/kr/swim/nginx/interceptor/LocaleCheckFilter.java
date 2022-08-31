package kr.swim.nginx.interceptor;

import kr.swim.nginx.util.AuthTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sw.im.swim.bean.dto.AdminEntityDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <PRE>
 * request locale 에서 timezone 얻어서 request에 저장한다.
 * </PRE>
 *
 * @author swim
 * @since 2021.09.22
 */
@Component
@Slf4j
public class LocaleCheckFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        log.debug("login check >>  " + request.getMethod() + "  " + request.getRequestURI());
//
//        boolean sessionAdmin = false;
//        try {
//
//            String token = request.getParameter("token");
//
//            if (token != null && token.length() > 5) {
//                String authToken = AuthTokenUtil.GET_AUTH_TOKEN();
//                sessionAdmin = authToken.equals(token);
//            }
//
//        } catch (Exception e) {
//        }
//
//        if (!sessionAdmin) {
//            response.setStatus(409);
//            log.debug("auth fail =>  ");
//            return false;
//        }
//
//        log.debug("you are admin");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
