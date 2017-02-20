package com.myretail.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.products.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Shreyas.
 */

@Configuration
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class})
@SpringBootApplication
@ComponentScan(basePackages = "com.myretail.products")
public class MyRetailApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyRetailApplication.class, args);

    }
    @Bean
    public ObjectMapper objectMapperSnakeCase() {
        return new Util().getSnakeCaseObjectMapper();
    }
}
