package interface_adapters.tui;

import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import database.Database;
import database.InMemoryDatabase;
import database.LocalDatabase;
import interactor.RecipeInteractor;
import interface_adapters.tui.controllers.*;
import usecase.*;

import java.util.List;
import java.util.Scanner;

/**
 * The main controller of the textual user interface.
 */
public class MainController {
    static final Scanner scanner = new Scanner(System.in);
    static final TextualReader reader = new TextualReader(scanner);

    public static void main(String[] args) throws Exception {
        // Silence Ormlite logging
        Logger.setGlobalLogLevel(Level.FATAL);

        // Initialize the database
        Database database;

        if (args.length > 1 && args[0].equals("--database") && args[1].equals("local")) {
            database = new LocalDatabase();
        } else {
            database = new InMemoryDatabase();
        }

        RecipeInteractor recipeInteractor = new RecipeInteractor(database);

        // Set up operations and run them
        List<TextualOperation> operations = List.of(
                new RecipeManagerOperation(reader, recipeInteractor),
                new RecipeTransferOperation(reader, new RecipeDataConverter(database), recipeInteractor),
                new RecipeLikerOperation(reader, new RecipeLiker(database)),
                new RecipeNoteOperation(reader, new RecipeNoteTaker(database), recipeInteractor),
                new RecipeSearcherOperation(reader, new RecipeSearcher(database)),
                new ChefModeOperation(reader, new ChefMode(database), recipeInteractor),
                new RecipeRecommenderOperation(reader, new RecipeRecommender(database))
        );

        Colour.info("Welcome to the Recipe Manager!");
        reader.chooseOperation(operations, "Home");

        // Shutdown
        scanner.close();
        Colour.info("Have a nice day");
    }
}
