package sw.im.swim.interceptor;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    private static final Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String id = null;
        try {
        } catch (Exception e) {
        }

        if (id == null || id.length() < 2) { // 로그인이 안되었다
            // 로그인이 아니되었으니 로그인폼페이지로 강제 이동시켜겠다
            response.sendRedirect("/login/requiredNotify");
            return false;
        } else {// 로그인이 되었다
            return true;
        }

//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        return false;

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
