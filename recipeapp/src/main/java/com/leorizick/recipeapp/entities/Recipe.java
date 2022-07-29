package com.leorizick.recipeapp.entities;

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

    @OneToMany(mappedBy = "stepsOrIngredients")
    private List<StepsAndIngredients> ingredients;

    @OneToMany(mappedBy = "stepsOrIngredients")
    private List<StepsAndIngredients> steps;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private RecipeCategory category;


}
