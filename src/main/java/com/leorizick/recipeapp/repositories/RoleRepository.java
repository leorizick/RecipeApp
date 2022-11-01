package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select distinct r.name from Profile p join p.roles r where p.name in :profilesList")
    Set<String> findAllDistinctByProfileList(Set<String> profilesList);
}
