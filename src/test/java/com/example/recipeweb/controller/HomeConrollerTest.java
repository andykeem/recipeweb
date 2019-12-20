package com.example.recipeweb.controller;

import com.example.recipeweb.entity.Recipe;
import com.example.recipeweb.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@WebMvcTest(RecipeConroller.class)
class HomeConrollerTest {

    private HomeConroller sut;

    @Mock
    private RecipeService recipeService;

//    @Autowired
//    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new HomeConroller(recipeService);
    }

    @Test
    public void test_home() {
        String expectedView = "home";
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe = mock(Recipe.class);
        recipeSet.add(recipe);

        when(recipeService.findAll()).thenReturn(recipeSet);

        Model model = mock(Model.class);
        String attrKey = "items";
        when(model.addAttribute(attrKey, recipeSet)).thenAnswer(new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                String s = invocationOnMock.getArgument(0);
                Set<Recipe> set = invocationOnMock.getArgument(1);

                assertEquals(attrKey, s);
                assertEquals(recipeSet, set);
                return null;
            }
        });

        Object view = sut.home(model);
        assertThat(view, is(instanceOf(String.class)));

        assertEquals(expectedView, view);

        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(attrKey, recipeSet);
        verify(model, never()).addAttribute(attrKey, Collections.EMPTY_SET);
        verify(model, times(1)).addAttribute(eq(attrKey), anySet());
    }

    @Test
    public void verifyModelAddAttr() {
        // given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe r = new Recipe();
        r.setDescription("Spicy Taco");
        recipes.add(r);

        when(recipeService.findAll()).thenReturn(recipes);

        // when
        Model model = mock(Model.class);
        sut.home(model);

        // then
        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);
        verify(model).addAttribute(eq("items"), captor.capture());

        Set<Recipe> items = captor.getValue();
        assertThat(items, is(equalTo(recipes)));
        assertThat(items.size(), is(equalTo(2)));
    }

    @Test
    public void controllerShouldReturnSuccessStatusAndHomeView() throws Exception {
        MockMvc mvc = MockMvcBuilders.standaloneSetup(sut).build();
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}