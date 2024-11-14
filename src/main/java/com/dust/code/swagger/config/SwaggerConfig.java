package com.dust.code.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is responsible for setting up the bean for swagger api.
 *
 * @author KashanAsim
 * @version 1.0
 * @project swagger
 * @created 11/14/2024
 * @lastModified 11/14/2024 by [Name of the person who last modified it]
 * @see <a href="https://www.linkedin.com/pulse/integrating-swagger-spring-boot-yaml-vs-annotations-k%25C4%2581sh%25C4%2581n-asim-gssve/?trackingId=Jo2WQPN7SdWLCoUz%2BUV3ig%3D%3D">...</a>
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {


    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        ClassPathResource resource = new ClassPathResource("swagger.yaml");
        Path path = Paths.get(resource.getURI());
        String content = new String(Files.readAllBytes(path));
        SwaggerParseResult result = new OpenAPIV3Parser().readContents(content);
        if (result.getMessages().isEmpty() && result.getOpenAPI() != null) {
            return result.getOpenAPI();
        } else {
            throw new RuntimeException("Failed to parse OpenAPI definition: " + result.getMessages());
        }
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api-docs/**").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/");
    }

}

