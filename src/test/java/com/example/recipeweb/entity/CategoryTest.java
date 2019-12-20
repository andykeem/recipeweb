package com.example.recipeweb.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class CategoryTest {

    private static final Long CATEGORY_ID = 123456L;
    private static final String CATEGORY_NAME = "Italian";
    private static final Set<Recipe> CATEGORY_RECIPES = new HashSet<>();

    private Category cat;

    @BeforeEach
    void setUp() {
        cat = new Category(CATEGORY_NAME);
        cat.setId(CATEGORY_ID);
        cat.setRecipes(CATEGORY_RECIPES);
    }

    @Test
    void getId() {
        long actual = cat.getId();
        long expected = CATEGORY_ID;
        assertEquals(expected, actual);

        long id = 1234;
        Category c = spy(Category.class);
        doReturn(id).when(c).getId();
        long cid = c.getId();
        assertEquals(id, cid);
    }

    @Test
    void getName() {
    }

    @Test
    void getRecipes() {
    }
}