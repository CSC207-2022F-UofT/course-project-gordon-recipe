package interface_adapters.tui.controllers;

import entity.Note;
import entity.Recipe;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import interactor.RecipeInteractor;
import usecase.RecipeNoteTaker;

import java.util.List;


/**
 * An operation for running the note taker use-case.
 */
public class RecipeNoteOperation implements TextualOperation {
    private final TextualReader reader;
    private final RecipeNoteTaker recipeNoteTaker;
    private final RecipeInteractor recipeInteractor;

    private final List<TextualOperation> operations = List.of(
            new NoteCreator(),
            new NoteViewer(),
            new NoteEditor(),
            new NoteDeleter()
    );

    public RecipeNoteOperation(TextualReader reader, RecipeNoteTaker recipeNoteTaker, RecipeInteractor recipeInteractor) {
        this.reader = reader;
        this.recipeNoteTaker = recipeNoteTaker;
        this.recipeInteractor = recipeInteractor;
    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Recipe Note Taker");
    }

    @Override
    public String getDescription() {
        return "Create, list, edit, and delete notes";
    }

    @Override
    public String getCode() {
        return "recipe notes";
    }

    private class NoteCreator implements TextualOperation {

        @Override
        public String getCode() {
            return "create";
        }

        @Override
        public String getDescription() {
            return "Create a new note";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeInteractor.getAllRecipes();

            if (recipes.size() < 1) {
                Colour.info("There are no recipes to add notes to.");
                return;
            }

            Recipe recipe = reader.chooseFromList(recipes, "Recipe");

            if (recipe == null) {
                return;
            }

            String noteText = reader.getInput("Note Text:");

            Note note = new Note(noteText, recipe);
            recipeNoteTaker.createNote(note);

            Colour.info("Created note %s", noteText);
        }
    }

    private class NoteViewer implements TextualOperation {
        @Override
        public String getCode() {
            return "view";
        }

        @Override
        public String getDescription() {
            return "View a recipe's notes";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeInteractor.getAllRecipes();

            Recipe recipe = reader.chooseFromList(recipes, "Recipe to View");

            if (recipe != null) {
                List<Note> notes = recipeInteractor.getNotes(recipe);

                if (notes.size() < 1) {
                    Colour.info("There are no notes on this recipe.");
                    return;
                }

                for (Note note : notes) {
                    System.out.println(note);
                }
            }
        }
    }

    private class NoteEditor implements TextualOperation {

        @Override
        public String getCode() {
            return "edit";
        }

        @Override
        public String getDescription() {
            return "Edit an existing note";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeInteractor.getAllRecipes();
            Recipe recipe = reader.chooseFromList(recipes, "Recipe to Edit Notes On");

            if (recipe == null) {
                return;
            }

            List<Note> notes = recipeInteractor.getNotes(recipe);
            Note note = reader.chooseFromList(notes, "Note to Edit");

            if (note == null) {
                return;
            }

            String noteText = reader.getInput("Note Text:");
            note.setText(noteText);

            recipeNoteTaker.updateNote(note);

            Colour.info("Edited note %s", noteText);
        }
    }

    private class NoteDeleter implements TextualOperation {
        @Override
        public String getCode() {
            return "delete";
        }

        @Override
        public String getDescription() {
            return "Delete a note";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeInteractor.getAllRecipes();
            Recipe recipe = reader.chooseFromList(recipes, "Recipe to Delete Notes On");

            if (recipe == null) {
                return;
            }

            List<Note> notes = recipeInteractor.getNotes(recipe);

            Note note = reader.chooseFromList(notes, "Note to Delete");

            if (note == null) {
                return;
            }

            recipeNoteTaker.deleteNote(note);
            Colour.info("Deleted note");
        }
    }
}
