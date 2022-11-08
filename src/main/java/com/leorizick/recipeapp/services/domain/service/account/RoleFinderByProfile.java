package com.leorizick.recipeapp.services.domain.service.account;

import com.leorizick.recipeapp.repositories.account.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleFinderByProfile {

    private final RoleRepository roleRepository;

    @Cacheable
    public Set<String> findAllDistinctByProfileList(Set<String> profileList){
        Set<String> roles = roleRepository.findAllDistinctByProfileList(profileList);
        return roles;
    }
}
