package interface_adapters.tui.controllers;

import entity.Note;
import entity.Recipe;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeNoteTaker;

import java.util.List;


/**
 * An operation for running the note taker use-case.
 */
public class RecipeNoteOperation implements TextualOperation{
    private final TextualReader reader;
    private final RecipeNoteTaker recipeNoteTaker;

    private final List<TextualOperation> operations = List.of(
            new ChooseRecipe(),
            new NoteCreator(),
            new NoteViewer(),
            new NoteEditor(),
            new NoteDeleter()
    );

    public RecipeNoteOperation(TextualReader reader, RecipeNoteTaker recipeNoteTaker) {
        this.reader = reader;
        this.recipeNoteTaker = recipeNoteTaker;
    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Recipe Note Taker");
    }

    @Override
    public String getDescription() {
        return "Create, list, edit, and delete note";
    }

    @Override
    public String getCode() {
        return "recipe note taker";
    }

    private class NoteCreator implements TextualOperation {
        private Note note;
        private Recipe recipe;

        @Override
        public String getCode() {
            return "create note";
        }

        @Override
        public String getDescription() {
            return "Create a new note";
        }

        @Override
        public void run() {
            String noteTitle = reader.getInput("Note Title:");
            note = new Note(noteTitle, recipe);

            recipeNoteTaker.createNote(note);

            Colour.info("Created note %s", noteTitle);
            reader.chooseOperation(operations, noteTitle);
        }

    }

    private class NoteViewer implements TextualOperation {
        @Override
        public String getCode() {
            return "view note";
        }

        @Override
        public String getDescription() {
            return "View a saved note";
        }

        @Override
        public void run() {
            List<Note> notes = recipeNoteTaker.getAllNotes();

            Note note = reader.chooseFromList(notes, "All Notes");

            if (note != null) {
                System.out.println(notes);
            }
        }

    }

    private class NoteEditor implements TextualOperation {
        private Note note;

        @Override
        public String getCode() {
            return "edit note";
        }

        @Override
        public String getDescription() {
            return "Edit an existing note";
        }

        @Override
        public void run() {
            List<Note> notes = recipeNoteTaker.getAllNotes();
            note = reader.chooseFromList(notes, "Note to Edit");

            if (note == null) {
                return;
            }

            reader.chooseOperation(operations, "Choose Edit");
        }
    }

    private class NoteDeleter implements TextualOperation {
        @Override
        public String getCode() {
            return "delete note";
        }

        @Override
        public String getDescription() {
            return "Delete a note";
        }

        @Override
        public void run() {
            List<Note> notes = recipeNoteTaker.getAllNotes();

            Note note = reader.chooseFromList(notes, "Recipe to Delete");

            if (note != null) {
                recipeNoteTaker.deleteNote(note);
                Colour.info("Deleted %s", note.getID());
            }
        }
    }
}