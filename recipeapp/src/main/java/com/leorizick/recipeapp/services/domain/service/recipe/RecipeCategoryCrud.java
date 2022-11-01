package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.repositories.RecipeCategoryRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeCategoryCrud {

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    public RecipeCategory findById(Long id) {
        return recipeCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria nao encontrada! Id: " + id));
    }

    public Page<RecipeCategory> findAll(Pageable pageable) {
        Page<RecipeCategory> recipeCategoryPage = recipeCategoryRepository.findAll(pageable);
        return recipeCategoryPage;
    }

    public RecipeCategory save(RecipeCategory recipeCategory) {
        return recipeCategoryRepository.save(recipeCategory);
    }

    public RecipeCategory update(RecipeCategory recipeCategory) {
        return recipeCategoryRepository.save(recipeCategory);
    }

    public void delete(Long id) {
        RecipeCategory recipeCategory = findById(id);
        try {
            recipeCategoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Nao é possivel excluir uma categoria que possua receitas!");
        }
    }

}
