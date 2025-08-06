package com.atm.buenas_practicas_java.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rutaAbsoluta = Paths.get(uploadDir).toAbsolutePath().toString();

        if (!rutaAbsoluta.endsWith("/")) {
            rutaAbsoluta += "/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + rutaAbsoluta);
    }
}