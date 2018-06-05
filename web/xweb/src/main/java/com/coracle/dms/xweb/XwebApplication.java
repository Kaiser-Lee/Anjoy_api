package com.coracle.dms.xweb;

import com.coracle.dms.xweb.common.resolver.InstantiationTracingBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ImportResource({"classpath:config/applicationContext-xweb-dubbo.xml"
        ,"classpath:config/applicationContext-xweb.xml"
        ,"classpath:config/applicationContext-jedis.xml"})
@Configuration
@ComponentScan
@ServletComponentScan
@EnableRedisHttpSession
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
public class XwebApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication springApplication =new SpringApplication(XwebApplication.class);
        springApplication.addListeners(new InstantiationTracingBeanPostProcessor());
        springApplication.run(args);
    }  

    /**
     * 上传附件容量限制
     * @return
     */
	@Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("512400KB");
        factory.setMaxRequestSize("512400KB");
        return factory.createMultipartConfig();  
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry.addResourceHandler("*//**")
                .addResourceLocations("classpath:index.html");
        registry.addResourceHandler("/assets*//**")
                .addResourceLocations("classpath:assets/");
        registry.addResourceHandler("/build*//**")
                .addResourceLocations("classpath:build/");*/
    }

	/**
	 * 配置拦截器
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
//        super.addInterceptors(registry);
    }
    
}  