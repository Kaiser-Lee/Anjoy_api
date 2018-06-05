package com.coracle.dms.xweb.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private String swaggerEnable;

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
     *
     */
    @Bean
    public Docket testApi() {
        System.out.println("===================>swaggerEnable:"+swaggerEnable);
        if("true".equalsIgnoreCase(swaggerEnable)) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("api_dms")
                    .genericModelSubstitutes(DeferredResult.class)
                    .useDefaultResponseMessages(false)
                    .forCodeGeneration(false)
                    .pathMapping("/")
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.coracle.dms.xweb.controller")).build();
        } else {
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("api_dms")
                    .genericModelSubstitutes(DeferredResult.class)
                    .useDefaultResponseMessages(false)
                    .forCodeGeneration(false)
                    .pathMapping("/")
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("no")).build();
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DMS渠橙RESTful API")
                .description("DMS渠橙中构建RESTful API")
                .termsOfServiceUrl("")
                .contact("huangbaidong")
                .version("1.0")
                .build();
    }
}