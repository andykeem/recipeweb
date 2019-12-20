package com.example.recipeweb.converter;

import com.example.recipeweb.command.IngredientCommand;
import com.example.recipeweb.command.UnitOfMeasureCommand;
import com.example.recipeweb.entity.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientCommandToIngredientTest {

    private static final long INGRED_ID = 1L;
    private static final String INGRED_DESCR = "lemon or lime juice";
    private static final BigDecimal INGRED_AMT = new BigDecimal(2);
    private static final long UOM_ID = 2L;
    private static final String UOM_VAL = "Tablespoon";
//    private static final long RECIPE_ID = 3L;

    UnitOfMeasureCommandToUnitOfMeasure uomConverter;
    NoteCommandToNote noteConverter;
    IngredientCommandToIngredient ingredConverter;
    CategoryCommandToCategory catConverter;
    RecipeCommandToRecipe recipeConverter;
    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
//        noteConverter = new NoteCommandToNote();
//        ingredConverter = new IngredientCommandToIngredient();
//        catConverter = new CategoryCommandToCategory();
        recipeConverter = new RecipeCommandToRecipe(noteConverter, ingredConverter, catConverter);
        converter = new IngredientCommandToIngredient(uomConverter); // , recipeConverter);
    }

    @Test
    public void givenNullIngredientCommandShouldReturnNullIngredient() {
        IngredientCommand ingredCmd = null;
        Ingredient ingred = converter.convert(ingredCmd);
        assertNull(ingred);
    }

    @Test
    public void givenIngredientCommandInstanceShouldNotReturnNullIngredient() {
        IngredientCommand ingredCmd = new IngredientCommand();
        Ingredient ingred = converter.convert(ingredCmd);
        assertNotNull(ingred);
    }

    @Test
    public void test_convert() {
        // given
        IngredientCommand ingredCmd = new IngredientCommand();
        ingredCmd.setId(INGRED_ID);
        ingredCmd.setDescription(INGRED_DESCR);
        ingredCmd.setAmount(INGRED_AMT);
        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(UOM_ID);
        uomCmd.setValue(UOM_VAL);
        ingredCmd.setUom(uomCmd);
//        RecipeCommand recipeCmd = new RecipeCommand();
//        recipeCmd.setId(RECIPE_ID);
//        ingredCmd.setRecipe(recipeCmd);

        // when
        Ingredient ingred = converter.convert(ingredCmd);

        // then
        assertThat(ingred.getId(), is(equalTo(INGRED_ID)));
        assertThat(ingred.getDescription(), is(equalTo(INGRED_DESCR)));
        assertThat(ingred.getAmount(), is(equalTo(INGRED_AMT)));
        assertThat(ingred.getUom().getId(), is(equalTo(UOM_ID)));
        assertThat(ingred.getUom().getValue(), is(equalTo(UOM_VAL)));
//        assertThat(ingred.getRecipe().getId(), is(equalTo(RECIPE_ID)));
    }
}