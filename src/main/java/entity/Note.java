package entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Note {
    /**
     * The ID of the note.
     */
    private String id;

    /**
     * The ID of the recipe the note is about.
     */
    private String recipeID;

    /**
     * The body text of the note.
     */
    private String text;

    /**
     * The date and time the note was created.
     */
    private LocalDateTime date;

    /**
     * Instantiates a new recipe note.
     * Generates a UUID for the note, and sets the date to today.
     *
     * @param text     The note's body text
     * @param recipeID The ID of the note's recipe
     */
    public Note(String text, String recipeID) {
        this.id = UUID.randomUUID().toString();
        this.recipeID = recipeID;
        this.text = text;
        this.date = LocalDateTime.now();
    }

    /**
     * Instantiates an empty note.
     */
    public Note() {

    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
