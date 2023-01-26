package com.leorizick.recipeapp.services.domain.service.account;


import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.repositories.account.AccountRepository;
import com.leorizick.recipeapp.services.exceptions.AlreadyDeletedException;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class AccountCrud {
    private static final String NOT_FOUND_MESSAGE = "Account %s not found";
    private final AccountRepository accountRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_MESSAGE, id)));
    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        Account account = findById(id);

        if (!account.isEnabled()) {
            throw new AlreadyDeletedException(Account.class, id.toString());
        }

        account.setEnabled(false);
        accountRepository.save(account);
    }

    public Page<Account> findAllAccountsByUsername(String username, Pageable pageable) {
        return  accountRepository.findAllAccountByUsernameStartsWithIgnoreCase(username, pageable);
    }
}
