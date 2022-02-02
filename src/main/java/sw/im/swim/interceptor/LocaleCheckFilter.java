package sw.im.swim.interceptor;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
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

        log.warn("login check");
        HttpSession session = request.getSession();

        final String contextPath = request.getContextPath();

        boolean sessionAdmin = false;
        try {
            AdminEntityDto adminEntityDto = (AdminEntityDto) session.getAttribute("admin");
            if (adminEntityDto.getSid() > 0) {
                sessionAdmin = true;
            }
        } catch (Exception e) {
        }

        if (!sessionAdmin) {
            response.sendRedirect(contextPath);
            log.warn("need login");
            return false;
        }

        log.warn("you are admin");
        return true;

//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
