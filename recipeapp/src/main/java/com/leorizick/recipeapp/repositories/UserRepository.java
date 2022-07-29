package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

     @Query
     User findByEmail(String email);
}
