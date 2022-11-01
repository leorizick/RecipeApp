package com.leorizick.recipeapp.services.domain.service.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.leorizick.recipeapp.dto.auth.AccountContextDetails;
import com.leorizick.recipeapp.services.domain.service.account.RoleFinderByProfile;
import com.leorizick.recipeapp.services.domain.service.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;

    private final RoleFinderByProfile roleFinderByProfile;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Requested {}", request.getRequestURL());

        validateSecurityContext(request);

        filterChain.doFilter(request, response);
    }

    private void validateSecurityContext(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            if (token.startsWith(jwtProperties.getPrefix())) {
                var context = decodeToken(token.replace(jwtProperties.getPrefix(), ""));
                var authentication = new UsernamePasswordAuthenticationToken(context, null, context.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    private AccountContextDetails decodeToken(String token) {
        var decoded = JWT.require(Algorithm.HMAC256(jwtProperties.getPassword()))
                .build()
                .verify(token);

        var profile = new HashSet<>(decoded.getClaim("profiles").asList(String.class));

        var roles = roleFinderByProfile.findAllDistinctByProfileList(profile).stream().collect(Collectors.toSet());

        return AccountContextDetails.builder()
                .id(Long.valueOf((decoded.getClaim("id").asString())))
                .email(decoded.getSubject())
                .profiles(profile)
                .roles(roles)
                .issuedAt(toLocalDateTime(decoded.getIssuedAt()))
                .expireAt(toLocalDateTime(decoded.getExpiresAt()))
                .build();
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public String tokenGenerate(AccountContextDetails accountContextDetails) {

        return JWT.create()
                .withClaim("id", accountContextDetails.getId().toString())
                .withSubject(accountContextDetails.getEmail())
                .withArrayClaim("profiles", accountContextDetails.getProfiles().toArray(new String[0]))
                .withClaim("credential", accountContextDetails.getCredentialId().toString())
                .withClaim("username", accountContextDetails.getUsername())
                .withIssuedAt(accountContextDetails.getIssuedAt().toInstant(ZoneOffset.UTC))
                .withExpiresAt(accountContextDetails.getExpireAt().toInstant(ZoneOffset.UTC))
                .sign(Algorithm.HMAC256(jwtProperties.getPassword()));
    }

}