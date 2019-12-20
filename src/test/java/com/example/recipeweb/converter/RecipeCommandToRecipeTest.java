package com.example.recipeweb.converter;

import com.example.recipeweb.command.IngredientCommand;
import com.example.recipeweb.command.NoteCommand;
import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.entity.Category;
import com.example.recipeweb.entity.Ingredient;
import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.enums.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RecipeCommandToRecipeTest {

    private static final long RECIPE_ID = 1L;
    private static final String RECIPE_DESCR = "Spicy Mexican Taco";
    private static final int RECIPE_PREP_TIME = 10;
    private static final Difficulty RECIPE_DIFFICULTY = Difficulty.EASY;
    private static final long RECIPE_NOTE_ID = 2L;
    private static final long RECIPE_INGRED_ID = 3L;
    private static final long RECIPE_CAT_ID = 4L;

    NoteCommandToNote noteConverter;
    UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    IngredientCommandToIngredient ingredConverter;
    CategoryCommandToCategory catConverter;
    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        noteConverter = new NoteCommandToNote();
        uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        ingredConverter = new IngredientCommandToIngredient(uomConverter);
        catConverter = new CategoryCommandToCategory();
        converter = new RecipeCommandToRecipe(noteConverter, ingredConverter, catConverter);
    }

    @Test
    public void nullRecipeCommandShouldReturnNull() {
        RecipeCommand recipeCmd = null;
        Recipe recipe = converter.convert(recipeCmd);
        assertNull(recipe);
    }

    @Test
    public void givenRecipeCommandShouldConvertToNotNullRecipe() {
        RecipeCommand recipeCmd = new RecipeCommand();
        Recipe recipe = converter.convert(recipeCmd);
        assertNotNull(recipe);
    }

    @Test
    public void test_convert() {
        // given
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(RECIPE_ID);
        cmd.setDescription(RECIPE_DESCR);
        cmd.setPrepTime(RECIPE_PREP_TIME);
        cmd.setDifficulty(RECIPE_DIFFICULTY);

        NoteCommand noteCmd = new NoteCommand();
        noteCmd.setId(RECIPE_NOTE_ID);
        cmd.setNote(noteCmd);

        Set<IngredientCommand> ingredCmds = new HashSet<>();
        IngredientCommand ingredCmd = new IngredientCommand();
        ingredCmd.setId(RECIPE_INGRED_ID);
        ingredCmds.add(ingredCmd);
        cmd.setIngredients(ingredCmds);

        /*Set<CategoryCommand> catCmds = new HashSet<>();
        CategoryCommand catCmd = new CategoryCommand();
        catCmd.setId(RECIPE_CAT_ID);
        catCmds.add(catCmd);
        cmd.setCategories(catCmds);*/

        Set<Long> catIds = new HashSet<>();
        catIds.add(RECIPE_CAT_ID);
        cmd.setCategoryIds(catIds);

        // when
        Recipe recipe = converter.convert(cmd);

        // then
        assertThat(recipe.getId(), is(equalTo(RECIPE_ID)));
        assertThat(recipe.getDescription(), is(equalTo(RECIPE_DESCR)));
        assertThat(recipe.getPrepTime(), is(equalTo(RECIPE_PREP_TIME)));
        assertThat(recipe.getDifficulty(), is(equalTo(RECIPE_DIFFICULTY)));
        assertThat(recipe.getNote().getId(), is(equalTo(RECIPE_NOTE_ID)));

        Ingredient ingred = recipe.getIngredients().iterator().next();
        assertThat(ingred.getId(), is(equalTo(RECIPE_INGRED_ID)));

        Category cat = recipe.getCategories().iterator().next();
        assertThat(cat.getId(), is(equalTo(RECIPE_CAT_ID)));
    }
}