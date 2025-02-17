package com.alten.ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "E-Commerce API", version = "v1", description = "API documentation for the E-Commerce application"))
@Configuration
public class OpenApiConfig {
}
