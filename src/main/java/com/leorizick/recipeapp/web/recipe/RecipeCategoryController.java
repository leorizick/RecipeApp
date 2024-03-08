package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCategoryCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryCrud service;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<RecipeCategory> find(@PathVariable Long id) {
        RecipeCategory category = service.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(category);
    }

    @GetMapping(value= "/api/category")
    public ResponseEntity<Page<RecipeCategory>> findAll(Pageable pageable) {
        Page<RecipeCategory> categories = service.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    @PostMapping
    public ResponseEntity<RecipeCategory> save(@RequestBody RecipeCategory recipeCategory) {
        RecipeCategory category = service.save(recipeCategory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(category);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RecipeCategory> update(@RequestBody RecipeCategory recipeCategory) {
        RecipeCategory category = service.update(recipeCategory);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
