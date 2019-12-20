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

class CategoryCommandToCategoryTest {

    private static final String CAT_NAME = "Italian";
    CategoryCommandToCategory converter;

    /*@Autowired
    RecipeCommandToRecipe recipeCmdToRecipe;*/

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory(); // recipeCmdToRecipe);
    }

    @Test
    public void nullCommandArgShouldReturnNull() {
        CategoryCommand cmd = null;
        Category cat = converter.convert(cmd);
        assertNull(cat);
    }

    @Test
    public void givenCommandCategoryShouldNotReturnNull() {
        CategoryCommand cmd = new CategoryCommand();
        Category cat = converter.convert(cmd);
        assertNotNull(cat);
    }

    @Test
    public void test_convert() {
        // given
        CategoryCommand cmd = new CategoryCommand();
        cmd.setName(CAT_NAME);

        // when
        Category cat = converter.convert(cmd);

        // then
        assertNotNull(cat.getName());
        assertThat(cat.getName(), is(equalTo(CAT_NAME)));
        assertThat(cat.getName(), is(equalTo(cmd.getName())));
    }
}












