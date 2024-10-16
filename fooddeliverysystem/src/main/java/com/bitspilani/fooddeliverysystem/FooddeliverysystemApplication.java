package com.bitspilani.fooddeliverysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FooddeliverysystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FooddeliverysystemApplication.class, args);
    }

}
