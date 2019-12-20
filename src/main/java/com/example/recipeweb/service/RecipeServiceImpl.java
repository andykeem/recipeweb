package com.example.recipeweb.service;

import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.converter.RecipeCommandToRecipe;
import com.example.recipeweb.converter.RecipeToRecipeCommand;
import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.exception.NotFoundException;
import com.example.recipeweb.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;
    private final RecipeCommandToRecipe recipeConverter;
    private final RecipeToRecipeCommand recipeCmdConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepo,
                             RecipeCommandToRecipe recipeConverter,
                             RecipeToRecipeCommand recipeCmdConverter) {
        this.recipeRepo = recipeRepo;
        this.recipeConverter = recipeConverter;
        this.recipeCmdConverter = recipeCmdConverter;
    }

    @Override
    public Set<Recipe> findAll() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepo.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(long id) {
        Optional<Recipe> optional = recipeRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
//        throw new EntityNotFoundException("Not found recipe id: " + id);
        throw new NotFoundException("Not found - recipe id: " + id);
    }

    @Override
    public RecipeCommand save(RecipeCommand recipeCmd) {
        Recipe recipe = recipeConverter.convert(recipeCmd);
        Recipe savedRecipe = recipeRepo.save(recipe);
        RecipeCommand cmdObj = recipeCmdConverter.convert(savedRecipe);
        return cmdObj;
    }
}
