package com.fengmi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//生成接口文档
public class SwaggerConfig {
        @Bean
    public Docket getDocket(){
//            创建封面信息对象
            ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
            apiInfoBuilder.title("<<锋迷商城后端接口说明>>")
                    .description("此文档详细说明了锋迷商城项目后端接口规范。")
                    .version("v 2.0.1")
                    .contact(new Contact("磊","www.yilei.com","1694620742@qq.com"));
            ApiInfo apiInfo = apiInfoBuilder.build();

            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.fengmi.controller"))
                    .paths(PathSelectors.any())
                    .build();
            return docket;
        }

}
