package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.Note;
import entity.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class RecipeNoteTakerTest {
    private RecipeNoteTaker taker;
    private Dao<Note, String> notes;

    private Recipe recipe;

    @BeforeEach
    public void Setup() throws SQLException {
        Database database = new InMemoryDatabase();

        taker = new RecipeNoteTaker(database);
        notes = database.getDao(Note.class);
        recipe = new Recipe("Pie", 10, 60);

        // Initialize recipe in the database for testing
        Dao<Recipe, String> recipes = database.getDao(Recipe.class);
        recipes.create(recipe);
    }

    @Test
    public void CreatingNoteTest() throws SQLException {
        Note text = new Note("This is note", recipe);
        taker.createNote(text);

        Note retrievedRecipe = notes.queryForId(text.getID());

        Assertions.assertEquals(text.getID(), retrievedRecipe.getID());
    }

    @Test
    public void UpdatingNoteTest() throws SQLException {
        Note text = new Note("This is note", recipe);
        taker.createNote(text);

        text.setText("This is updated text");
        taker.updateNote(text);

        Note retrievedNote = notes.queryForId(text.getID());

        Assertions.assertEquals("This is updated text", retrievedNote.getText());
    }

    @Test
    public void DeletingNoteTest() throws SQLException {
        Note text = new Note("This is a note", recipe);

        taker.createNote(text);

        Assertions.assertEquals(1, notes.countOf());

        taker.deleteNote(text);

        Assertions.assertEquals(0, notes.countOf());
    }

    @Test
    public void GetAllNotesTest() throws SQLException {
        Note note1 = new Note("This is note 1", recipe);
        Note note2 = new Note("This is note 2", recipe);

        List<Note> notesList = List.of(note1, note2);

        notes.create(notesList);

        List<Note> retrievedNotes = taker.getAllNotes();
        System.out.println(notesList);

        Assertions.assertEquals(2, retrievedNotes.size());

//        Assertions.assertEquals("This is note 1"+"This is note 2", retrievedNotes.toString());
//        List<String> expected = Arrays.asList("This is note 1", "This is note 2");
//        Assertions.assertArrayEquals(expected.toArray(), retrievedNotes.toArray());

    }
}
