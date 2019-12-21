package com.example.recipeweb.entity;

import javax.persistence.*;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String text;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "fk_note_recipe"))
    private Recipe recipe;

    public Note() {
    }

    public Note(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
