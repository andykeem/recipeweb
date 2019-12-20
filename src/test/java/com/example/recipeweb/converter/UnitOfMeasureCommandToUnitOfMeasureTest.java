package com.example.recipeweb.converter;

import com.example.recipeweb.command.UnitOfMeasureCommand;
import com.example.recipeweb.entity.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final long UOM_ID = 1L;
    private static final String UOM_VAL = "Teaspoon";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void nullSourceShouldReturnNull() {
        UnitOfMeasureCommand uomCmd = null;
        UnitOfMeasure uom = converter.convert(uomCmd);
        assertNull(uom);
    }

    @Test
    public void whenSourceIsNotNullShouldReturnNotNull() {
        UnitOfMeasureCommand src = new UnitOfMeasureCommand();
        UnitOfMeasure uom = converter.convert(src);
        assertNotNull(uom);
    }

    @Test
    public void verifyConversion() {
        // given
        UnitOfMeasureCommand src = new UnitOfMeasureCommand();
        src.setId(UOM_ID);
        src.setValue(UOM_VAL);

        // when
        UnitOfMeasure uom = converter.convert(src);

        // then
        assertThat(uom.getId(), is(equalTo(src.getId())));
        assertThat(uom.getValue(), is(equalTo(src.getValue())));
    }
}