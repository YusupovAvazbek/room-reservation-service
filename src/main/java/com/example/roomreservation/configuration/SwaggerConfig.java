package com.example.roomreservation.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Swagger API",
                description = "",
                termsOfService = "",
                contact = @Contact(name = "", email = "yusupofavazbek@gmail.com", url = "https://t.me//YusupovAvazbek")
        ))

public class SwaggerConfig {

}
