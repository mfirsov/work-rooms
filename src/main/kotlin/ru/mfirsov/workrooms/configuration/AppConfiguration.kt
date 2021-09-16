package ru.mfirsov.workrooms.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
@EnableOpenApi
class AppConfiguration {
    @Bean
    fun docket(): Docket =
        Docket(DocumentationType.OAS_30)
            .pathMapping("/")
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
            .paths(PathSelectors.any())
            .build()
            .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
            .consumes(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
}