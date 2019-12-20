package com.example.recipeweb.controller;

import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping({"", "/"})
public class HomeConroller {

    private final RecipeService recipeService;

    public HomeConroller(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String home(Model model) {
        Set<Recipe> items = recipeService.findAll();
        model.addAttribute("items", items);
        return "home";
    }
}