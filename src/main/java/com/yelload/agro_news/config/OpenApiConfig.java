package com.yelload.agro_news.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Agro News API")
                    .description("Spring shop sample application")
                    .version("v0.0.1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }



}
