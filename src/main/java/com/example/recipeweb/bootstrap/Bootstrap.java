package com.example.recipeweb.bootstrap;

import com.example.recipeweb.entity.*;
import com.example.recipeweb.enums.Difficulty;
import com.example.recipeweb.helper.Image;
import com.example.recipeweb.repository.CategoryRepository;
import com.example.recipeweb.repository.RecipeRepository;
import com.example.recipeweb.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final String CLASSPATH = "src/main/resources/";

    private RecipeRepository recipeRepo;
    private UnitOfMeasureRepository uomRepo;

    public Bootstrap(RecipeRepository recipeRepo, UnitOfMeasureRepository uomRepo, CategoryRepository catRepo) {
        this.recipeRepo = recipeRepo;
        this.uomRepo = uomRepo;
        this.catRepo = catRepo;
    }

    private CategoryRepository catRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        init();
    }

    private void init() {
        log.info("Bootstrap.init() method called...");

        try {
            if (catRepo.count() == 0) {
                loadCategories();
            }
            if (uomRepo.count() == 0) {
                loadUnitOfMeasures();
            }
            if (recipeRepo.count() == 0) {
                List<Recipe> recipes = new ArrayList<>(2);
                recipes.add(getPerfectGuacamoleRecipe());
                recipes.add(getSpicyGrilledChickenTacosRecipe());
                recipeRepo.saveAll(recipes);
            }
        } catch (SQLGrammarException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private Recipe getPerfectGuacamoleRecipe() {
        String description = "Perfect Guacamole";
        Integer prepTime = 10;
        Integer cookTime = 20;
        Integer servings = 3;
        String source = "Simply Receipes";
        String url = "https://www.simplyrecipes.com/recipes/perfect_guacamole";
        String direction = "1 Cut avocado, remove flesh" +
                "\n2 Mash with a fork" +
                "\n3 Add salt, lime juice, and the rest" +
                "\n4 Cover with plastic and chill to store";
        Difficulty difficulty = Difficulty.EASY;
        String path = CLASSPATH + "static/img/guacamole.jpg"; // new ClassPathResource("static/img/guacamole.jpg").getPath();
        Byte[] image = new Image().getBytesByPaty(path);

        Recipe recipe = new Recipe(description, prepTime, cookTime, servings, source, url, direction, difficulty, image);

        Note note = new Note("The BEST guacamole! EASY to make with ripe avocados, salt, serrano chiles, " +
                "cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla chips.");
        recipe.setNote(note);

        addIngredient("avocaods", 2, "Ripe", recipe);
        addIngredient("kosher salt", 0.5, "Teaspoon", recipe);
        addIngredient("fresh lime or lemon juice", 1, "Tablespoon", recipe);
        addIngredient("minced red onion or thinly sliced green onion", 0.25, "Cup", recipe);
        addIngredient("freshly grated black pepper", 1, "Dash", recipe);

        addCategory("Mexican", recipe);

        return recipe;
    }

    private Recipe getSpicyGrilledChickenTacosRecipe() {
        String description = "Spicy Grilled Chicken Tacos";
        int prepTime = 20;
        int cookTime = 15;
        int servings = 5;
        String source = "Simply Receipes";
        String url = "https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos";
        String direction = "1 Prepare a gas or charcoal grill for medium-high, direct heat." +
                "\n2 Make the marinade and coat the chicken" +
                "\n3 Grill the chicken" +
                "\n4 Warm the tortillas" +
                "\n5 Assemble the tacos";
        Difficulty difficulty = Difficulty.MODERATE;
        String path = CLASSPATH + "static/img/chicken-tacos.jpg";
        Byte[] image = new Image().getBytesByPaty(path);

        Recipe recipe = new Recipe(description, prepTime, cookTime, servings, source, url, direction, difficulty, image);

        Note note = new Note("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. " +
                "Great for a quick weeknight dinner, backyard cookouts, and tailgate parties. ");
        recipe.setNote(note);

        addIngredient("ancho chili powder", 2, "Tablespoon", recipe);
        addIngredient("dried oregano", 1, "Teaspoon", recipe);
        addIngredient("dried cumin", 1, "Teaspoon", recipe);
        addIngredient("sugar", 1, "Teaspoon", recipe);
        addIngredient("salt", 0.5, "Teaspoon", recipe);
        addIngredient("finely grated orange zest", 1, "Tablespoon", recipe);
        addIngredient("fresh-squeezed orange juice", 3, "Tablespoon", recipe);
        addIngredient("olive oil", 2, "Tablespoon", recipe);

        addCategory("Mexican", recipe);

        return recipe;
    }

    private void addIngredient(String description, double val, String unit, Recipe recipe) {
        uomRepo.findByValue(unit).map(new Function<UnitOfMeasure, UnitOfMeasure>() {
            @Override
            public UnitOfMeasure apply(UnitOfMeasure unitOfMeasure) {
                return unitOfMeasure;
            }
        }).ifPresent(new Consumer<UnitOfMeasure>() {
            @Override
            public void accept(UnitOfMeasure unitOfMeasure) {
//                recipe.getIngredients().add(new Ingredient(description, new BigDecimal(val), unitOfMeasure, recipe));
                recipe.addIngredient(new Ingredient(description, new BigDecimal(val), unitOfMeasure));
            }
        });
    }

    private void addCategory(String catName, Recipe recipe) {
        catRepo.findByName(catName).ifPresent(new Consumer<Category>() {
            @Override
            public void accept(Category category) {
//                recipe.getCategories().add(category);
                recipe.addCategory(category);
            }
        });
    }

    private void loadCategories() {
        List<String> items = Arrays.asList("American", "Italian", "Mexican", "Fast Food");
        items.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                catRepo.save(new Category(s));
            }
        });
    }

    private void loadUnitOfMeasures() {
        List<String> items = Arrays.asList("Teaspoon", "Tablespoon", "Cup", "Pinch", "Ounce", "Dash", "Ripe");
        items.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                uomRepo.save(new UnitOfMeasure(s));
            }
        });
    }
}