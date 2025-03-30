package com.example.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.dataaccess.repository"})
@ComponentScan(basePackages = {"com.example.*"})
@EntityScan(basePackages = {"com.example.dataaccess.entity"})
@OpenAPIDefinition(info = @Info(title = "OnlineShop API", version = "1.0" , description = "java onlineshop"))
@EnableCaching
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        System.out.println("hello temp");
    }

}
