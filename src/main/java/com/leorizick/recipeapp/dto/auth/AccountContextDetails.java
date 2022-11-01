package com.leorizick.recipeapp.dto.auth;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class AccountContextDetails implements UserDetails, Serializable {

    private Long id;

    private Long credentialId;

    private String username;

    private String email;

    private String password;

    private boolean accountEnabled;

    private boolean credentialEnabled;

    private Set<String> profiles;

    private Set<String> roles;

    private LocalDateTime issuedAt;

    private LocalDateTime expireAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return credentialEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return LocalDateTime.now().isBefore(expireAt);
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled;
    }

}