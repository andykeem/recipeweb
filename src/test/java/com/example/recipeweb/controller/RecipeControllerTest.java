package com.example.recipeweb.controller;

import com.example.recipeweb.command.RecipeCommand;
import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.exception.NotFoundException;
import com.example.recipeweb.service.CategoryService;
import com.example.recipeweb.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    RecipeController controller;
    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;

    @Mock
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RecipeController(recipeService, categoryService);
    }

    @Test
    public void test_viewAction() throws Exception {
        long id = 2L;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/view/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/view"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findById(id);
    }

    @Test
    public void verifyRecipeSetToModelAttribute() {
        // given
        long id = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        when(recipeService.findById(id)).thenReturn(recipe);

        // when
        Model model = mock(Model.class);
        controller.view(id, model);

        ArgumentCaptor<String> arg1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Recipe> arg2 = ArgumentCaptor.forClass(Recipe.class);

        // then
        verify(model).addAttribute(arg1.capture(), arg2.capture());

        String attrKey = arg1.getValue();
        Recipe foundRecipe = arg2.getValue();

        assertThat(attrKey, is(equalTo("recipe")));
        assertThat(foundRecipe, is(equalTo(recipe)));
    }

    @Test
    public void whenRecipeNotFound_throw_EntityNotFoundException() {
        long id = 0L;
        Model model = mock(Model.class);

        when(recipeService.findById(id)).thenThrow(new EntityNotFoundException());

        String expected = "recipe/not-found.html";
        String actual = controller.view(id, model);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void test_createAction() throws Exception {
        // given
        long id = 2L;
        RecipeCommand cmdObj = new RecipeCommand();
        cmdObj.setId(id);

        RecipeController controllerSpy = spy(controller);
        doReturn(cmdObj).when(controllerSpy).recipeCommand();

        // when
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controllerSpy).build();

        // then
        mockMvc.perform(get("/recipe/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/create"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", cmdObj));

        verify(controllerSpy, times(1)).recipeCommand();
    }

    @Test
    public void test_postAction() throws Exception {
        String description = "New recipe description...";
        RecipeCommand recipe = new RecipeCommand();
        recipe.setDescription(description);

        long id = 2L;
        RecipeCommand savedRecipe = new RecipeCommand(id);
        when(recipeService.save(recipe)).thenReturn(savedRecipe);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/recipe/post")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", description))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/view/" + id));

        verify(recipeService, times(1)).save(recipe);
    }

    @Test
    public void invalidViewIdShouldThrowException() throws Exception {
        // given
        long badId = 2L;

        // when
        when(recipeService.findById(badId)).thenThrow(NotFoundException.class);

        // then
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        String badUrl = "/recipe/view/" + badId;
        mockMvc.perform(get(badUrl))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error404"));

        verify(recipeService).findById(badId);
    }

    @Test
    public void stringIdArgumentShouldThrowMethodArgumentTypeMismatchException() throws Exception {
        String strId = "two";

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(GlobalController.class)
                .build();

        mockMvc.perform(get("/recipe/view/" + strId))
                .andExpect(status().isBadRequest());

        verify(recipeService, never()).findById(anyLong());
    }
}