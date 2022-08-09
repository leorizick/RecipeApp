package com.leorizick.recipeapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String name;
    private String description;
    private Boolean isActive;
    private String ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeSteps> steps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RecipeCategory category;

    public Recipe(Long id, String name, String description, Boolean isActive, String ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.ingredients = ingredients;
    }
}
