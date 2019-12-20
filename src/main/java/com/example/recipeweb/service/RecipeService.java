package com.example.recipeweb.service;

import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.entity.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> findAll();
    Recipe findById(long id);
    RecipeCommand save(RecipeCommand recipe);
}
