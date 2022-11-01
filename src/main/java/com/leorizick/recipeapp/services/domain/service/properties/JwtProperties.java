package com.leorizick.recipeapp.services.domain.service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("recipeapp.security.jwt")
public class JwtProperties {

    private String prefix = "Bearer ";

    private String password;

    private Long expirationTime;

}
