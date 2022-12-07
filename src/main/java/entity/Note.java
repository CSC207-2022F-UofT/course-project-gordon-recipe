package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import database.LocalDateTimePersister;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@DatabaseTable(tableName = "notes")
public class Note implements Serializable {
    /**
     * The ID of the note.
     */
    @DatabaseField(id = true, canBeNull = false)
    private String id;

    /**
     * The recipe the note is about.
     */
    @DatabaseField(canBeNull = false, foreign = true)
    private Recipe recipe;

    /**
     * The body text of the note.
     */
    @DatabaseField(canBeNull = false, width = 2000)
    private String text;

    /**
     * The date and time the note was created.
     */
    @DatabaseField(canBeNull = false, persisterClass = LocalDateTimePersister.class)
    private LocalDateTime date;

    /**
     * Instantiates a new recipe note.
     * Generates a UUID for the note, and sets the date to today.
     *
     * @param text   The note's body text
     * @param recipe The note's recipe
     */
    public Note(String text, Recipe recipe) {
        this.id = UUID.randomUUID().toString();
        this.recipe = recipe;
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

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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

    @Override
    public String toString() {
        return String.format("%s: %s", date.format(DateTimeFormatter.ISO_DATE), text);
    }
}
