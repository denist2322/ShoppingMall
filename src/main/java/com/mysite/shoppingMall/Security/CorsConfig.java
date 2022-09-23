package com.mysite.shoppingMall.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// == Cors 문제를 해결하기 위한 Config ==
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // CORS(백엔드 에서 다른 URL로 들어오는 요청) 를 막는다.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
