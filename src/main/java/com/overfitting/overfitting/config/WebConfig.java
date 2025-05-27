package com.overfitting.overfitting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // ✅ 프론트엔드 SPA 라우팅 대상만 명시
        registry.addViewController("/webtoon-file").setViewName("forward:/index.html");
        registry.addViewController("/edit/{title}/{chapter}").setViewName("forward:/index.html");
        // ⚠️ 그 외 경로는 절대 forward하지 않음 (특히 /api/**)
    }
}
