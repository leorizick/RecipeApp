package com.leorizick.recipeapp.rest;

import com.leorizick.recipeapp.entities.Recipe;
import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.services.RecipeCategoryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class RecipeCategoryController {

    @Autowired
    private RecipeCategoryservice service;

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<RecipeCategory> find(Long id) {
        RecipeCategory category = service.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(category);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<RecipeCategory>> findAll(Pageable pageable) {
        Page<RecipeCategory> categories = service.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categories);
    }

    @PostMapping
    public ResponseEntity<RecipeCategory> save(@RequestBody RecipeCategory recipeCategory){
        RecipeCategory category = service.save(recipeCategory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(category);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RecipeCategory> update(@RequestBody RecipeCategory recipeCategory){
        RecipeCategory category = service.update(recipeCategory);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
