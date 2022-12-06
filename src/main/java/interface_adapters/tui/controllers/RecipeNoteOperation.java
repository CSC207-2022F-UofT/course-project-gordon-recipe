package interface_adapters.tui.controllers;

import entity.Note;
import entity.Recipe;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeManager;
import usecase.RecipeNoteTaker;

import java.util.List;


/**
 * An operation for running the note taker use-case.
 */
public class RecipeNoteOperation implements TextualOperation {
    private final TextualReader reader;
    private final RecipeNoteTaker recipeNoteTaker;
    private final RecipeManager recipeManager;

    private final List<TextualOperation> operations = List.of(new NoteCreator(), new NoteViewer(), new NoteEditor(), new NoteDeleter());

    public RecipeNoteOperation(TextualReader reader, RecipeNoteTaker recipeNoteTaker, RecipeManager recipeManager) {
        this.reader = reader;
        this.recipeNoteTaker = recipeNoteTaker;
        this.recipeManager = recipeManager;
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
            String title = reader.getInput("Recipe Title:");
            Integer servings = reader.getIntegerInput("Servings (e.g. 10):");
            Integer prep_time = reader.getIntegerInput("Minutes of prep time:");

            Recipe recipe = new Recipe(title, servings, prep_time);


            String noteText = reader.getInput("Note Text:");

            Note note = new Note(noteText, recipe);

            recipeNoteTaker.createNote(note);

            Colour.info("Created note %s", noteText);
            reader.chooseOperation(operations, "end");
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
                System.out.println(note);
            }
        }

    }

    private class NoteEditor implements TextualOperation {

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
            Note note = reader.chooseFromList(notes, "Note to Edit");

            if (note == null) {
                return;
            }

            String noteText = reader.getInput("Note Text:");
            note.setText(noteText);
            //note = new Note(noteText, recipe);

            recipeNoteTaker.updateNote(note);

            Colour.info("Edited note %s", noteText);
            reader.chooseOperation(operations, "end");

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
