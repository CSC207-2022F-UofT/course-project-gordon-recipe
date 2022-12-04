package interface_adapters.tui;

import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import database.Database;
import database.InMemoryDatabase;
import database.LocalDatabase;
import interface_adapters.tui.controllers.RecipeManagerOperation;
import interface_adapters.tui.controllers.RecipeTransferOperation;
import usecase.RecipeManager;

import java.util.List;
import java.util.Scanner;

/**
 * The main controller of the textual user interface.
 */
public class MainController {
    static Scanner scanner = new Scanner(System.in);
    static TextualReader reader = new TextualReader(scanner);

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

        // Set up operations and run them
        List<TextualOperation> operations = List.of(
                new RecipeManagerOperation(reader, new RecipeManager(database)),
                new RecipeTransferOperation(reader, database)
        );

        Colour.info("Welcome to the Recipe Manager!");
        reader.chooseOperation(operations, "Home");

        // Shutdown
        scanner.close();
        Colour.info("Have a nice day");
    }
}
