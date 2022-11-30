package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.Note;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RecipeNoteTaker {
    private final Dao<Note, String> notes;

    /**
     * Initializes a corresponding note taker base on the recipe.
     *
     * @param database the database to store note in
     */
    public RecipeNoteTaker(Database database) {
        this.notes = database.getDao(Note.class);
    }

    /**
     * Stores a note in the database.
     *
     * @param note the note to store
     */
    public void createNote(Note note) {
        try {
            notes.create(note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a note in the database.
     *
     * @param note the note to update
     */
    public void updateNote(Note note) {
        try {
            notes.update(note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a note in the database.
     *
     * @param note the note to delete
     */
    public void deleteNote(Note note) {
        try {
            notes.delete(note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of all notes in the database.
     *
     * @return all notes
     */
    public List<Note> getAllNotes() {
        ArrayList<Note> noteList = new ArrayList<>();

        for (Note note : notes) {
            noteList.add(note);
        }

        return noteList;
    }
}
