package interface_adapters.tui.controllers;

import database.Database;
import entity.Recipe;
import entity.RecipeData;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeDataConverter;
import usecase.RecipeManager;

import javax.swing.*;
import java.io.*;
import java.util.List;

/**
 * An operation for handling the use case of importing and exporting recipes.
 */
public class RecipeTransferOperation implements TextualOperation {
    private final TextualReader reader;
    private final RecipeDataConverter converter;
    private final RecipeManager recipeManager;

    private final List<TextualOperation> operations = List.of(
            new RecipeImporter(),
            new RecipeExporter()
    );

    public RecipeTransferOperation(TextualReader reader, Database database) {
        this.reader = reader;
        this.converter = new RecipeDataConverter(database);
        this.recipeManager = new RecipeManager(database);
    }

    @Override
    public String getCode() {
        return "transfer";
    }

    @Override
    public String getDescription() {
        return "Import and export recipe files";
    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Import/Export Recipe");
    }

    private class RecipeExporter implements TextualOperation {
        @Override
        public String getCode() {
            return "export";
        }

        @Override
        public String getDescription() {
            return "Save a recipe as a local file";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeManager.getAllRecipes();
            Recipe recipe = reader.chooseFromList(recipes, "Recipe to Export");
            if (recipe == null) {
                return;
            }
            RecipeData recipeData = converter.exportRecipe(recipe);

            JFileChooser fc = new JFileChooser();
            forceDialogFocus();
            if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = fc.getSelectedFile();

            try {
                FileOutputStream f = new FileOutputStream(file);
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(recipeData);
                o.flush();
                o.close();
            } catch (Exception e) {
                Colour.error("Could not save %s to %s", recipe.getName(), file.getName());
                return;
            }
            Colour.info("Saved %s to %s", recipe.getName(), file.getName());
        }
    }

    private class RecipeImporter implements TextualOperation {
        @Override
        public String getCode() {
            return "import";
        }

        @Override
        public String getDescription() {
            return "Import a saved recipe file";
        }

        @Override
        public void run() {
            JFileChooser fc = new JFileChooser();
            forceDialogFocus();
            if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File file = fc.getSelectedFile();

            RecipeData recipeData;
            try {
                FileInputStream fi = new FileInputStream(file);
                ObjectInputStream si = new ObjectInputStream(fi);
                recipeData = (RecipeData) si.readObject();
                si.close();
            } catch (Exception e) {
                Colour.error("Could not import from %s", file.getName());
                return;
            }

            converter.importRecipe(recipeData);
            Colour.info("Imported from %s", file.getName());
        }
    }

    /**
     * Does some magic so that the file open/save dialogs actually appear prominently
     * instead of opening silently behind a thousand other things and being impossible
     * to get to without Alt+Tab. Code derived from
     * <a href="https://stackoverflow.com/a/7536734">this StackOverflow answer</a>.
     */
    private void forceDialogFocus(){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.ICONIFIED);
        frame.setExtendedState(JFrame.NORMAL);
        frame.dispose();
    }
}
