
//Namespace
package com.ecommerce.catalog.config;

//Imports
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  Configuration class for Open Api
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(@Value("${spring.application.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Product Catalog Service")
                        .description("This is the Product Catalog Open API Documentation.")
                        .version(appVersion)
                        .contact(new Contact().name("SoftwareTastic!").email("enquires@SoftwareTastic.com").url("https://www.SoftwareTastic.com/"))
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );
    }
}

