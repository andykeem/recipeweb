package com.example.recipeweb.command;

import com.example.recipeweb.enums.Difficulty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String direction;
    private Difficulty difficulty;
    private Byte[] image;
    private NoteCommand note;
    private Set<IngredientCommand> ingredients;
    private Set<Long> categoryIds = new HashSet<>();

    public RecipeCommand(Long id) {
        this.id = id;
    }
}
