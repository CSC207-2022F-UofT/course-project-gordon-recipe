package interface_adapters.tui;

import database.Database;
import database.InMemoryDatabase;
import interface_adapters.tui.controllers.RecipeManagerOperation;
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
        Database database = new InMemoryDatabase();

        List<TextualOperation> operations = List.of(
                new RecipeManagerOperation(reader, new RecipeManager(database))
        );

        Colour.println(Colour.GREEN_BOLD, "Welcome to the Recipe Manager!");
        reader.chooseOperation(operations, "Home");

        scanner.close();
        Colour.println(Colour.GREEN_BOLD, "Have a nice day");
    }
}
