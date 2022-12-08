package interface_adapters.tui.controllers;

import entity.*;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeSearcher;

import java.util.ArrayList;
import java.util.List;


/**
 * An operation for running the recipe recommender use-case.
 */
public class RecipeSearcherOperation implements TextualOperation {

    private final TextualReader reader;
    private final RecipeSearcher recipeSearcher;

    private final List<TextualOperation> operations = List.of(
            new RecipeIngredientSearcher(),
            new RecipeToolSearcher(),
            new RecipeTagSearcher()
    );

    public RecipeSearcherOperation(TextualReader reader, RecipeSearcher recipeSearcher) {
        this.reader = reader;
        this.recipeSearcher = recipeSearcher;

    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Recipe Searcher");
    }

    @Override
    public String getDescription() {
        return "Search through the recipes with certain ingredients, tools, or tags";
    }

    @Override
    public String getCode() {
        return "recipe searcher";
    }

    private void finishSearch(List<PreparationItem> searchList) {
        List<Recipe> recipes = recipeSearcher.searchRecipe(searchList);
        StringBuilder output = new StringBuilder();
        for (Recipe recipe : recipes) {
            output.append(recipe.getName()).append(", ");
        }
        if (output.toString().trim().endsWith(",")) {
            output = new StringBuilder(output.substring(0, output.toString().trim().length() - 1));
        }
        Colour.info("Here is the list of recipes: \n%s\n", output.toString());
    }

    private class RecipeIngredientSearcher implements TextualOperation {
        @Override
        public String getCode() {
            return "ingredient search";
        }

        @Override
        public String getDescription() {
            return "Search through the recipes with certain ingredients";
        }

        @Override
        public void run() {
            List<PreparationItem> searchList = new ArrayList<>();

            while (true) {
                String name = reader.getInput("Ingredient %d (type %s to search):",
                        searchList.size() + 1, Colour.example("start"));
                Ingredient ingredient = (Ingredient) recipeSearcher.inDatabase(name, "Ingredient");
                if (name.equals("start")) {
                    break;
                } else if (ingredient != null) {
                    searchList.add(ingredient);
                }
            }

            finishSearch(searchList);
        }
    }

    private class RecipeToolSearcher implements TextualOperation {
        @Override
        public String getCode() {
            return "tool search";
        }

        @Override
        public String getDescription() {
            return "Search through the recipes with certain tools";
        }

        @Override
        public void run() {
            List<PreparationItem> searchList = new ArrayList<>();

            while (true) {
                String name = reader.getInput("Tool %d (type %s to search):",
                        searchList.size() + 1, Colour.example("start"));
                Tool tool = (Tool) recipeSearcher.inDatabase(name, "Tool");
                if (name.equals("start")) {
                    break;
                } else if (tool != null) {
                    searchList.add(tool);
                }
            }

            finishSearch(searchList);
        }
    }

    private class RecipeTagSearcher implements TextualOperation {
        @Override
        public String getCode() {
            return "tag search";
        }

        @Override
        public String getDescription() {
            return "Search through the recipes with certain tags";
        }

        @Override
        public void run() {
            List<PreparationItem> searchList = new ArrayList<>();

            while (true) {
                String name = reader.getInput("Tag %d (type %s to search):",
                        searchList.size() + 1, Colour.example("start"));
                Tag tag = (Tag) recipeSearcher.inDatabase(name, "Tag");
                if (name.equals("start")) {
                    break;
                } else if (tag != null) {
                    searchList.add(tag);
                }
            }

            finishSearch(searchList);
        }
    }
}
