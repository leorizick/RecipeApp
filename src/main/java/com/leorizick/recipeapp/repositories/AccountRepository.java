package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
