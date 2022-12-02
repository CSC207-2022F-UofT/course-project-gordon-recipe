package interface_adapters.tui.controllers;

import entity.Recipe;
import entity.Step;
import entity.Tag;
import entity.Tool;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An operation for running the recipe manager use-case.
 */
public class RecipeManagerOperation implements TextualOperation {
    private final TextualReader reader;
    private final RecipeManager recipeManager;

    private final List<TextualOperation> operations = List.of(
            new RecipeCreator(),
            new RecipeLister(),
            new RecipeDeleter()
    );

    public RecipeManagerOperation(TextualReader reader, RecipeManager recipeManager) {
        this.reader = reader;
        this.recipeManager = recipeManager;
    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Recipe Manager");
    }

    @Override
    public String getDescription() {
        return "Create, list, and edit recipes";
    }

    @Override
    public String getCode() {
        return "recipe manager";
    }

    private class RecipeCreator implements TextualOperation {
        private Recipe recipe;

        private final List<TextualOperation> operations = List.of(
                new ToolAdder(),
                new TagAdder(),
                new StepAdder()
        );

        @Override
        public String getCode() {
            return "create recipe";
        }

        @Override
        public String getDescription() {
            return "Create a new recipe";
        }

        @Override
        public void run() {
            String title = reader.getInput("Recipe Title:");
            Integer servings = reader.getIntegerInput("Servings (e.g. 10):");
            Integer prep_time = reader.getIntegerInput("Minutes of prep time:");

            recipe = new Recipe(title, servings, prep_time);

            recipeManager.createRecipe(recipe);

            System.out.printf("Creating recipe %s with %d servings and %d prep time\n", title, servings, prep_time);
            reader.chooseOperation(operations, title);
        }

        private class ToolAdder implements TextualOperation {

            @Override
            public String getCode() {
                return "add tools";
            }

            @Override
            public String getDescription() {
                return "Add tools to the recipe";
            }

            @Override
            public void run() {
                List<Tool> tools = reader.getList("Tool").stream().map(Tool::new).collect(Collectors.toList());
                recipeManager.createRecipeTools(recipe, tools);

                System.out.printf("Added %d tools to the recipe.", tools.size());
            }
        }

        private class TagAdder implements TextualOperation {

            @Override
            public String getCode() {
                return "add tags";
            }

            @Override
            public String getDescription() {
                return "Add tags to the recipe";
            }

            @Override
            public void run() {
                List<Tag> tags = reader.getList("Tag").stream().map(Tag::new).collect(Collectors.toList());
                recipeManager.createRecipeTags(recipe, tags);

                System.out.printf("Added %d tags to the recipe", tags.size());
            }
        }

        private class StepAdder implements TextualOperation {

            @Override
            public String getCode() {
                return "add steps";
            }

            @Override
            public String getDescription() {
                return "Add a steps to the recipe";
            }

            @Override
            public void run() {
                List<String> stepTexts = reader.getList("Step");
                List<Step> steps = new ArrayList<>();

                int counter = 0;

                for (String stepText : stepTexts) {
                    Step step = new Step(stepText, counter, recipe);
                    steps.add(step);
                    counter += 1;
                }

                recipeManager.createRecipeSteps(steps);
                System.out.printf("Added %d steps to the recipe", steps.size());
            }
        }
    }

    private class RecipeLister implements TextualOperation {

        @Override
        public String getCode() {
            return "list recipes";
        }

        @Override
        public String getDescription() {
            return "List all recipes";
        }

        @Override
        public void run() {
            ArrayList<Recipe> recipes = recipeManager.getAllRecipes();

            for (Recipe recipe : recipes) {
                System.out.printf("%s: %d servings, %d min. prep time\n", recipe.getName(), recipe.getServings(), recipe.getPrepTime());
            }
        }
    }

    private class RecipeDeleter implements TextualOperation {

        @Override
        public String getCode() {
            return "delete recipe";
        }

        @Override
        public String getDescription() {
            return "Delete a recipe";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeManager.getAllRecipes();
            List<String> recipeNames = recipes.stream().map(Recipe::getName).collect(Collectors.toList());

            Colour.printHeader("Delete Recipe");

            Integer indexToDelete = reader.getListIndexInput(recipeNames, "Which recipe should be deleted?");

            if(indexToDelete != null) {
                Recipe recipeToDelete = recipes.get(indexToDelete);

                recipeManager.deleteRecipe(recipeToDelete);
                System.out.printf("Deleted %s\n", recipeToDelete.getName());
            }
        }
    }
}
