package com.example.recipeweb.controller;

import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.enums.Difficulty;
import com.example.recipeweb.exception.NotFoundException;
import com.example.recipeweb.service.CategoryService;
import com.example.recipeweb.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @ModelAttribute(name = "recipe")
    public RecipeCommand recipeCommand() {
        return new RecipeCommand();
    }

    @ModelAttribute
    public void loadDifficulties(Model model) {
        Difficulty[] diffs = Difficulty.values();
        model.addAttribute(diffs);
    }

    @ModelAttribute
    public void loadCategories(Model model) {
        model.addAttribute(categoryService.findAll());
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        log.debug("/recipe/view/" + id);
        try {
            Recipe recipe = recipeService.findById(id);
            model.addAttribute("recipe", recipe);
            return "recipe/view";
        } catch (EntityNotFoundException e) {
            return "recipe/not-found.html";
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        log.debug("/recipe/create GET request init..");
        log.debug("model: " + model);
        return "recipe/create";
    }

    @PostMapping("/post")
    public String post(@Valid @ModelAttribute("recipe") RecipeCommand recipe, Errors err, Model model) {
        log.debug("recipe: " + recipe);
        if (err.hasErrors()) {
            return "recipe/create";
        }
        RecipeCommand cmdObj = recipeService.save(recipe);
        String redirect = String.format("redirect:/recipe/view/%d", cmdObj.getId());
        return redirect;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NotFoundException.class})
    public ModelAndView handleNotFound(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error404");
        mav.addObject("exception", ex);
        return mav;
    }
}