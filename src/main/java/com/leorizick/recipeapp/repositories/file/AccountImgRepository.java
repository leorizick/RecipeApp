package com.leorizick.recipeapp.repositories.file;

import com.leorizick.recipeapp.entities.file.AccountImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountImgRepository extends JpaRepository<AccountImg, Long > {

    @Modifying
    @Query("delete from AccountImg ai where ai.account.id = :accountId")
    void deleteByRecipeAndAccountId(Long accountId);

    @Query("select ai from AccountImg ai where ai.account.id = :accountId")
    AccountImg getAccountImage(Long accountId);
}
