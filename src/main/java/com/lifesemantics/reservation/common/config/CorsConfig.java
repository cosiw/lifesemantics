package com.lifesemantics.reservation.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//            .allowedOrigins("http://localhost:8800")
//            .allowedMethods("GET", "POST", "PUT", "DELETE")
//            .allowedHeaders("*");
//    }

}
