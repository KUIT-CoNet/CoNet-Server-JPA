package com.kuit.conet.config;

import com.kuit.conet.utils.StringToEnumConverter;
import com.kuit.conet.utils.auth.BearerAuthInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final UserIdResolver userIdResolver;
    private final ClientIpResolver clientIpResolver;
    private final BearerAuthInterceptor bearerAuthInterceptor;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userIdResolver);
        resolvers.add(clientIpResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }

    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Interceptor 등록");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/auth/regenerate-token");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/auth/term-and-name");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/auth/option-term");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/user");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/user/name");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/user/image");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/user/delete");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team/{teamId}");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team/{teamId}/members");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team/create");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team/join");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team/leave");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/user/bookmark");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/team/bookmark/delete");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/plan/fixed");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/plan/available-time-slot");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/plan/{planId}/available-time-slot/my");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/home/plan/month");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/home/plan/day");
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/home/plan/waiting");

    }
}