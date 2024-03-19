package com.hkbu.comp7640.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi createRestApi() {
        return GroupedOpenApi.builder()
                .group("接口文档")
                .packagesToScan("com.hkbu.comp7640")
                .build();
    }

    @Bean
    public OpenAPI createOpenApi() {
        return new OpenAPI()
                .info(new Info().title("comp7640项目接口文档")
                        .description("comp7640项目接口文档， openAPI3.0接口，用于前端对接")
                        .version("v1.0.0"));
    }
}
