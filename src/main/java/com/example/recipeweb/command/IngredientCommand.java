package com.example.recipeweb.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
//    private RecipeCommand recipe;
}
