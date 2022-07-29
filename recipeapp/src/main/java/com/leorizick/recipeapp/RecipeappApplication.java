package com.leorizick.recipeapp;

import com.leorizick.recipeapp.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeappApplication implements CommandLineRunner {

	@Autowired
	private RecipeRepository recipeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RecipeappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
