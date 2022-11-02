package com.leorizick.recipeapp.config;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RestAssuredConfig {

    @Value("${server.port:8080}")
    private int localServerPort;

    @Bean
    public void restAssured() {
        log.info("Setting RestAssured to use port {}", localServerPort);
        RestAssured.port = localServerPort;
    }

}
