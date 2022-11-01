package com.leorizick.recipeapp.services.domain.service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "beroad")
public class RecipeAppProperties {

    private SecurityProperties security;
}
