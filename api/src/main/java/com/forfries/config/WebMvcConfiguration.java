package com.forfries.config;

import com.forfries.interceptor.JwtTokenSystemAdminInterceptor;
import com.forfries.interceptor.JwtTokenCinemaAdminInterceptor;
import com.forfries.interceptor.JwtTokenUserInterceptor;
import com.forfries.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private JwtTokenSystemAdminInterceptor jwtTokenSystemAdminInterceptor;

    @Autowired
    private JwtTokenCinemaAdminInterceptor jwtTokenCinemaAdminInterceptor;

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册系统管理员拦截器...");
        registry.addInterceptor(jwtTokenSystemAdminInterceptor)
                .addPathPatterns("/api/admin/movies/**")
                .addPathPatterns("/api/admin/cinemas/**");

        log.info("开始注册影院管理员拦截器...");
        registry.addInterceptor(jwtTokenCinemaAdminInterceptor)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns("/api/admin/movies/**")
                .excludePathPatterns("/api/admin/cinemas/**");

        log.info("开始注册用户拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/api/user/**");

    }

    /**
     * 扩展Spring MVC框架的消息转化器
     * @param converters
     */
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        //将自己的消息转化器加入容器中
        converters.add(0,converter);
    }
}
