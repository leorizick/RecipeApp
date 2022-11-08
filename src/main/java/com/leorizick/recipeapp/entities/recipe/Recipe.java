package com.leorizick.recipeapp.entities.recipe;

import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.entities.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private String name;
    private String description;
    private boolean enabled;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeStep> step;

    @OneToOne(optional = false)
    @JoinColumn(name = "category_id")
    private RecipeCategory category;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account author;

    @OneToMany(mappedBy = "recipe")
    @Where(clause = "enabled = true")
    private List<Comment> comment;

}
