package sw.im.swim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import sw.im.swim.interceptor.ApiCheckFilter;
import sw.im.swim.interceptor.LocaleCheckFilter;
import sw.im.swim.interceptor.ViewFilter;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/api/**").allowedOrigins("*")
                .allowedMethods(new String[]{"GET", "POST", "PATCH", "PUT", "DELETE"});
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // WebMvcConfigurer.super.addInterceptors(registry);

        final String[] resourcePatterns = {
                "resources/**",
                "resource/**",
                "**/favicon.ico",
                "**/swagger-ui.html",
                "webjars/**"
        };

        final String[] viewPatterns = {
                "/view/*",
                "/view/**",
        };

        final String[] apiPatterns = {
                "/api/**",
                "/api/*",
        };

        final String[] loginPatterns = {
                "/login/**",
                "/logout/*",
                "test/**"
        };


        registry.addInterceptor(new ApiCheckFilter())
                .addPathPatterns(apiPatterns)
                .excludePathPatterns(viewPatterns)
                .excludePathPatterns(resourcePatterns)
                .excludePathPatterns(loginPatterns);

        registry.addInterceptor(new ViewFilter())
                .addPathPatterns(viewPatterns)
                .excludePathPatterns(apiPatterns)
                .excludePathPatterns(resourcePatterns)
                .excludePathPatterns(loginPatterns);


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}

