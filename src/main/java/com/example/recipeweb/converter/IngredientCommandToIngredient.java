package com.example.recipeweb.converter;

import com.example.recipeweb.command.IngredientCommand;
import com.example.recipeweb.entity.Ingredient;
import com.example.recipeweb.entity.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
//    private final RecipeCommandToRecipe recipeConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter
//                                         ,RecipeCommandToRecipe recipeConverter
                                         ) {
        this.uomConverter = uomConverter;
//        this.recipeConverter = recipeConverter;
    }

    @Synchronized
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient target = new Ingredient();
        target.setId(source.getId());
        target.setDescription(source.getDescription());
        target.setAmount(source.getAmount());

        UnitOfMeasure uom = uomConverter.convert(source.getUom());
        target.setUom(uom);

//        Recipe recipe = recipeConverter.convert(source.getRecipe());
//        target.setRecipe(recipe);

        return target;
    }
}
