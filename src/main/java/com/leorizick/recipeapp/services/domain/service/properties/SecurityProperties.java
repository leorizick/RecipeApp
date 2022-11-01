package com.leorizick.recipeapp.services.domain.service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("recipeapp.security")
public class SecurityProperties {

    private JwtProperties jwt;

}
