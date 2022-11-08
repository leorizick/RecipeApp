package com.leorizick.recipeapp.services.domain.service.account;


import com.leorizick.recipeapp.entities.account.Profile;
import com.leorizick.recipeapp.repositories.account.ProfileRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfileFinderByName {

    private final ProfileRepository profileRepository;

    @Cacheable
    public Profile findByName(String name){
        Optional<Profile> profile = profileRepository.findByName(name);
        return profile.orElseThrow(() -> new NotFoundException("Profile not found! Name: " + name));
    }
}
