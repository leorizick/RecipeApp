package com.leorizick.recipeapp.entities.like;

import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RecipeLike {

    @EmbeddedId
    @Delegate
    private RecipeLikePK id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @MapsId("recipeId")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @MapsId("accountId")
    private Account account;

    public RecipeLike (Recipe recipe, Account account){
        id = new RecipeLikePK();
        setRecipe(recipe);
        setAccount(account);
    }

}
