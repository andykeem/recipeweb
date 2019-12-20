package com.example.recipeweb.converter;

import com.example.recipeweb.command.IngredientCommand;
import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.entity.Category;
import com.example.recipeweb.entity.Ingredient;
import com.example.recipeweb.entity.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NoteCommandToNote noteConverter;
    private final IngredientCommandToIngredient ingredConverter;
    private final CategoryCommandToCategory catConverter;

    public RecipeCommandToRecipe(NoteCommandToNote noteConverter,
                                 IngredientCommandToIngredient ingredConverter,
                                 CategoryCommandToCategory catConverter) {
        this.noteConverter = noteConverter;
        this.ingredConverter = ingredConverter;
        this.catConverter = catConverter;
    }

    @Synchronized
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe target = new Recipe();
        target.setId(source.getId());
        target.setDescription(source.getDescription());
        if (source.getPrepTime() != null) {
            target.setPrepTime(source.getPrepTime());
        }
        if (source.getCookTime() != null) {
            target.setCookTime(source.getCookTime());
        }
        if (source.getServings() != null) {
            target.setServings(source.getServings());
        }
        target.setSource(source.getSource());
        target.setUrl(source.getUrl());
        target.setDirection(source.getDirection());
        target.setDifficulty(source.getDifficulty());
        target.setImage(source.getImage());
        target.setNote(noteConverter.convert(source.getNote()));

        if (source.getIngredients() != null && !source.getIngredients().isEmpty()) {
            source.getIngredients().forEach(new Consumer<IngredientCommand>() {
                @Override
                public void accept(IngredientCommand ingredCmd) {
                    Ingredient ingred = ingredConverter.convert(ingredCmd);
                    target.getIngredients().add(ingred);
                }
            });
        }

        if (source.getCategoryIds() != null && !source.getCategoryIds().isEmpty()) {
            source.getCategoryIds().forEach(new Consumer<Long>() {
                @Override
                public void accept(Long catId) {
                    Category c = new Category();
                    c.setId(catId);
                    target.getCategories().add(c);
                }
            });
        }

        return target;
    }
}
