package com.example.recipeweb.converter;

import com.example.recipeweb.command.CategoryCommand;
import com.example.recipeweb.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoryToCategoryCommandTest {

    private static final long CAT_ID = 1L;
    private static final String CAT_NAME = "French";

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void nullCategoryShouldReturnNullCategoryCommand() {
        Category cat = null;
        CategoryCommand catCmd = converter.convert(cat);
        assertNull(catCmd);
    }

    @Test
    public void givenCategoryShouldReturnNotNullCategoryCommand() {
        Category cat = new Category();
        CategoryCommand catCmd = converter.convert(cat);
        assertNotNull(catCmd);
    }

    @Test
    public void test_convert() {
        Category cat = new Category();
        cat.setId(CAT_ID);
        cat.setName(CAT_NAME);
        CategoryCommand catCmd = converter.convert(cat);

        assertThat(catCmd.getId(), is(equalTo(CAT_ID)));
        assertThat(catCmd.getName(), is(equalTo(CAT_NAME)));
    }
}