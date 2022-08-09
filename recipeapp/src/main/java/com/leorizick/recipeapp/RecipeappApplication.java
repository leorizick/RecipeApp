package com.leorizick.recipeapp;

import com.leorizick.recipeapp.entities.Recipe;
import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.entities.RecipeSteps;
import com.leorizick.recipeapp.repositories.RecipeCategoryRepository;
import com.leorizick.recipeapp.repositories.RecipeRepository;
import com.leorizick.recipeapp.repositories.RecipeStepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class RecipeappApplication implements CommandLineRunner {

	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private RecipeCategoryRepository categoryRepository;
	@Autowired
	private RecipeStepsRepository stepsRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RecipeappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		RecipeCategory recipeCategory = new RecipeCategory(null, "Doces");
		categoryRepository.save(recipeCategory);

		Recipe recipe = new Recipe(null, "Receita de bolinhos", "Como fazer bolinhos", true, "Açucar, farinha, ovos, leite");
		recipe.setCategory(recipeCategory);
		recipeRepository.save(recipe);

		RecipeSteps rs1 = new RecipeSteps(null, "Adicionar tudo ao liquidificador", recipe);
		RecipeSteps rs2 = new RecipeSteps(null, "Bater por 5 minutos", recipe);
		RecipeSteps rs3 = new RecipeSteps(null, "Levar em uma forma e assar por 15 minutos a 180 graus", recipe);
		stepsRepository.saveAll(Arrays.asList(rs1,rs2,rs3));

		recipe.getSteps().addAll(Arrays.asList(rs1,rs2,rs3));


	}

}
