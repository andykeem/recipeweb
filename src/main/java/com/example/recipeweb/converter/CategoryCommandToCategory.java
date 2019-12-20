package com.example.recipeweb.converter;

import com.example.recipeweb.command.CategoryCommand;
import com.example.recipeweb.entity.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    /*private final RecipeCommandToRecipe recipeCommandToRecipe;

    public CategoryCommandToCategory(RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }*/

    @Synchronized
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }
        final Category target = new Category();
        target.setId(source.getId());
        target.setName(source.getName());

        /*if (source.getRecipes() != null) {
            Set<RecipeCommand> set = source.getRecipes();
            set.forEach(new Consumer<RecipeCommand>() {
                @Override
                public void accept(RecipeCommand recipeCommand) {
                    Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
                    target.getRecipes().add(recipe);
                }
            });
        }*/

        return target;
    }
}
