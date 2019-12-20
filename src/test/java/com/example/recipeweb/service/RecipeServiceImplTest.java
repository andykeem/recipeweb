package com.example.recipeweb.service;

import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.converter.RecipeCommandToRecipe;
import com.example.recipeweb.converter.RecipeToRecipeCommand;
import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    private RecipeServiceImpl sut;

    @Mock
    private RecipeRepository recipeRepo;

    @Mock
    private RecipeCommandToRecipe recipeConverter;

    @Mock
    private RecipeToRecipeCommand recipeCmdConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new RecipeServiceImpl(recipeRepo, recipeConverter, recipeCmdConverter);
    }

    @Test
    public void test_findAll() {
        Set<Recipe> recipes = new HashSet<>();
        Recipe recipe = new Recipe();
        recipes.add(recipe);
        when(recipeRepo.findAll()).thenReturn(recipes);

        Set<Recipe> actual = sut.findAll();
        assertEquals(recipes, actual);

        verify(recipeRepo, times(1)).findAll();
    }

    @Test
    public void test_findById() {
        long id = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> optional = Optional.of(recipe);
        when(recipeRepo.findById(id)).thenReturn(optional);

        Recipe newRecipe = sut.findById(id);
        assertThat(newRecipe, is(equalTo(recipe)));

        verify(recipeRepo, times(1)).findById(id);
    }

    @Test
    public void test_save() {
        // given
        String desription = "New recipe description";
        RecipeCommand input = new RecipeCommand();
        input.setDescription(desription);

        Recipe recipe = new Recipe();
        recipe.setDescription(desription);

        long id = 2L;
        Recipe savedRecipe = new Recipe(id);
        savedRecipe.setDescription(desription);
        RecipeCommand cmdObj = new RecipeCommand(id);
        cmdObj.setDescription(desription);

        when(recipeConverter.convert(input)).thenReturn(recipe);
        when(recipeRepo.save(recipe)).thenReturn(savedRecipe);

        // when
        sut.save(input);

        // then
        verify(recipeConverter).convert(input);
        verify(recipeRepo).save(recipe);
        verify(recipeCmdConverter).convert(savedRecipe);
    }
}