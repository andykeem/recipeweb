package com.example.recipeweb.converter;

import com.example.recipeweb.command.NoteCommand;
import com.example.recipeweb.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NoteCommandToNoteTest {

    private static final long NOTE_ID = 1L;
    private static final String NOTE_TEXT = "A very quick and easy fix to a tasty side-dish. Fancy, designer mac and cheese often costs forty or fifty dollars to prepare when you have so many exotic and expensive cheeses, but they aren't always the best tasting. This recipe is cheap and tasty.";

    NoteCommandToNote converter;

    @BeforeEach
    void setUp() {
        converter = new NoteCommandToNote();
    }

    @Test
    public void nullNoteCommandShouldReturnNullNote() {
        NoteCommand noteCmd = null;
        Note note = converter.convert(noteCmd);
        assertNull(note);
    }

    @Test
    public void givenNoteCommandShouldReturnNotNullNote() {
        NoteCommand noteCmd = new NoteCommand();
        Note note = converter.convert(noteCmd);
        assertNotNull(note);
    }

    @Test
    public void test_convert() {
        NoteCommand noteCmd = new NoteCommand();
        noteCmd.setId(NOTE_ID);
        noteCmd.setText(NOTE_TEXT);

        Note note = converter.convert(noteCmd);
        assertThat(note.getId(), is(equalTo(NOTE_ID)));
        assertThat(note.getText(), is(equalTo(NOTE_TEXT)));
    }
}