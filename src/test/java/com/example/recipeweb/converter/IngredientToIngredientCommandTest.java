package com.example.recipeweb.converter;

import com.example.recipeweb.command.IngredientCommand;
import com.example.recipeweb.command.UnitOfMeasureCommand;
import com.example.recipeweb.entity.Ingredient;
import com.example.recipeweb.entity.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientToIngredientCommandTest {

    private static final long INGRED_ID = 1L;
    private static final String INGRED_DESCR = "lime or lemon juice";
    private static final BigDecimal INGRED_AMT = new BigDecimal(2);
    private static final long UOM_ID = 2L;

    UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        uomConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        converter = new IngredientToIngredientCommand(uomConverter);
    }

    @Test
    public void givenNullIngredientShouldReturnNull() {
        Ingredient ingred = null;
        IngredientCommand ingredCmd = converter.convert(ingred);
        assertNull(ingredCmd);
    }

    @Test
    public void givenIngredientShouldConvertToNotNullIngredient() {
        Ingredient ingred = new Ingredient();
        IngredientCommand ingredCmd = converter.convert(ingred);
        assertNotNull(ingredCmd);
    }

    @Test
    public void test_convert() {
        Ingredient ingred = new Ingredient();
        ingred.setId(INGRED_ID);
        ingred.setDescription(INGRED_DESCR);
        ingred.setAmount(INGRED_AMT);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);
        ingred.setUom(uom);

        IngredientCommand ingredCmd = converter.convert(ingred);

        assertThat(ingredCmd.getId(), is(equalTo(INGRED_ID)));
        assertThat(ingredCmd.getDescription(), is(equalTo(INGRED_DESCR)));
        assertThat(ingredCmd.getAmount(), is(equalTo(INGRED_AMT)));

        UnitOfMeasureCommand uomCmd = ingredCmd.getUom();
        assertThat(uomCmd.getId(), is(equalTo(UOM_ID)));
    }
}