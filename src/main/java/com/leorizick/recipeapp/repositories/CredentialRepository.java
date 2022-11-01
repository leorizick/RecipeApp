package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.account.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    @Query("select c from Credential c where c.email = :email or c.account.username = :email")
    Optional<Credential> findByEmailOrAccountUsername(String email);

    @Query("select c from Credential c where c.email = :email and c.enabled = true")
    Optional<Credential> findByEmailAndIsEnabledTrue(String email);

}
