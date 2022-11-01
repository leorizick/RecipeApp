package com.leorizick.recipeapp.services.domain.service.datetime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @Bean
    public void timeZone(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
