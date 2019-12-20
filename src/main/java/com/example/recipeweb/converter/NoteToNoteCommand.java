package com.example.recipeweb.converter;

import com.example.recipeweb.command.NoteCommand;
import com.example.recipeweb.entity.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {

    @Synchronized
    @Override
    public NoteCommand convert(Note source) {
        if (source == null) {
            return null;
        }

        final NoteCommand target = new NoteCommand();
        target.setId(source.getId());
        target.setText(source.getText());
        return target;
    }
}
