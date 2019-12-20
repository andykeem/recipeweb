package com.example.recipeweb.converter;

import com.example.recipeweb.command.NoteCommand;
import com.example.recipeweb.entity.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    /*private final RecipeCommandToRecipe recipeConverter;

    public NoteCommandToNote(RecipeCommandToRecipe recipeConverter) {
        this.recipeConverter = recipeConverter;
    }*/

    @Synchronized
    @Override
    public Note convert(NoteCommand source) {
        if (source == null) {
            return null;
        }

        final Note target = new Note();
        target.setId(source.getId());
        target.setText(source.getText());
//        target.setRecipe(recipeConverter.convert(source.getRecipe()));
        return target;
    }
}
