package com.leorizick.recipeapp.services;

import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.repositories.RecipeCategoryRepository;
import com.leorizick.recipeapp.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeCategoryservice {

    @Autowired
    private RecipeCategoryRepository repository;

    public RecipeCategory find(Long id) {
        Optional<RecipeCategory> category = repository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Categoria nao encontrada! Id: " + id));
    }

    public Page<RecipeCategory> findAll(Pageable pageable) {
        Page<RecipeCategory> recipeCategoryPage = repository.findAll(pageable);
        return recipeCategoryPage;
    }

    public RecipeCategory save(RecipeCategory recipeCategory) {
        return repository.save(recipeCategory);
    }

    public RecipeCategory update(RecipeCategory recipeCategory) {
        return repository.save(recipeCategory);
    }

    public void delete(Long id) {
        RecipeCategory recipeCategory = find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Nao é possivel excluir uma categoria que possua receitas!");
        }
    }

}
