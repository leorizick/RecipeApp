package com.leorizick.recipeapp.repositories.account;

import com.leorizick.recipeapp.entities.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findAllAccountByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
