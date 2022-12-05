package interface_adapters.tui.controllers;

import entity.*;
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
            new RecipeViewer(),
            new RecipeEditor(),
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
        private final List<TextualOperation> operations = List.of(
                new ToolAdder(),
                new TagAdder(),
                new StepAdder(),
                new IngredientAdder()
        );

        private Recipe recipe;

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

            Colour.info("Created recipe %s", title);
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

                Colour.info("Added %d tools to the recipe", tools.size());
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

                Colour.info("Added %d tags to the recipe", tags.size());
            }
        }

        private class StepAdder implements TextualOperation {

            @Override
            public String getCode() {
                return "add steps";
            }

            @Override
            public String getDescription() {
                return "Add steps to the recipe";
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
                Colour.info("Added %d steps to the recipe", steps.size());
            }
        }

        private class IngredientAdder implements TextualOperation {

            @Override
            public String getCode() {
                return "add ingredients";
            }

            @Override
            public String getDescription() {
                return "Add ingredients to the recipe";
            }

            @Override
            public void run() {
                List<RecipeIngredient> recipeIngredients = new ArrayList<>();

                while (true) {
                    String name = reader.getInput("Ingredient %d (type %s to quit):", recipeIngredients.size() + 1, Colour.example("exit"));

                    if (name.equals("exit")) {
                        break;
                    }

                    String quantity = reader.getInput("Quantity of %s (e.g. 1 cup or 10):", name);

                    Ingredient ingredient = new Ingredient(name);
                    RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, quantity);

                    recipeIngredients.add(recipeIngredient);
                }

                recipeManager.createRecipeIngredients(recipeIngredients);

                Colour.info("Added %d ingredients to the recipe.", recipeIngredients.size());
            }
        }
    }

    private class RecipeViewer implements TextualOperation {

        @Override
        public String getCode() {
            return "view recipe";
        }

        @Override
        public String getDescription() {
            return "View a saved recipe";
        }

        @Override
        public void run() {
            ArrayList<Recipe> recipes = recipeManager.getAllRecipes();

            Recipe recipe = reader.chooseFromList(recipes, "All Recipes");

            if (recipe != null) {
                System.out.println(recipeDetail(recipe));
            }
        }

        private String recipeDetail(Recipe recipe) {
            String servings = String.format("Serves %d", recipe.getServings());
            String prep = String.format("%dm prep time", recipe.getPrepTime());

            String tools = recipeManager
                    .getTools(recipe)
                    .stream()
                    .map(t -> String.format("- %s\n", t.getName()))
                    .collect(Collectors.joining());

            String ingredients = recipeManager
                    .getRecipeIngredients(recipe)
                    .stream()
                    .map(i -> String.format("- %s\n", i.toString()))
                    .collect(Collectors.joining());

            String steps = recipeManager
                    .getSteps(recipe)
                    .stream()
                    .map(s -> String.format("%d. %s\n", s.getNumber() + 1, s.getText()))
                    .collect(Collectors.joining());

            String[] parts = {
                    "", header(recipe.getName()), servings, prep, "",
                    header("Tools"), tools,
                    header("Ingredients"), ingredients,
                    header("Steps"), steps
            };

            return String.join("\n", parts);
        }

        private String header(String section) {
            return String.format("%s\n=====", Colour.colour(Colour.PURPLE, section));
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

            Recipe recipe = reader.chooseFromList(recipes, "Recipe to Delete");

            if (recipe != null) {
                recipeManager.deleteRecipe(recipe);
                Colour.info("Deleted %s", recipe.getName());
            }
        }
    }

    private class RecipeEditor implements TextualOperation {
        private final List<TextualOperation> operations = List.of(
                new FrontMatterEditor()
        );

        private Recipe recipe;

        @Override
        public String getCode() {
            return "edit recipe";
        }

        @Override
        public String getDescription() {
            return "Edit an existing recipe";
        }

        @Override
        public void run() {
            List<Recipe> recipes = recipeManager.getAllRecipes();
            recipe = reader.chooseFromList(recipes, "Recipe to Edit");

            if (recipe == null) {
                return;
            }

            reader.chooseOperation(operations, "Choose Edit");
        }

        private class FrontMatterEditor implements TextualOperation {

            @Override
            public String getCode() {
                return "edit front matter";
            }

            @Override
            public String getDescription() {
                return String.format("Edit %s's title, servings, and prep time", recipe.getName());
            }

            @Override
            public void run() {
                String name = reader.getInput("Title (%s):", recipe.getName());
                Integer servings = reader.getIntegerInput("Servings (%d):", recipe.getServings());
                Integer prepTime = reader.getIntegerInput("Prep Time (%d):", recipe.getPrepTime());

                recipe.setName(name);
                recipe.setServings(servings);
                recipe.setPrepTime(prepTime);

                recipeManager.updateRecipe(recipe);
            }
        }
    }
}
