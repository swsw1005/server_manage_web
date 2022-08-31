package kr.swim.nginx.config;

import kr.swim.nginx.interceptor.LocaleCheckFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        final String[] apiPatterns = {
                "/api/**",
                "/api/*",
        };


        registry.addInterceptor(new LocaleCheckFilter())
                .addPathPatterns(apiPatterns);


    }
}
