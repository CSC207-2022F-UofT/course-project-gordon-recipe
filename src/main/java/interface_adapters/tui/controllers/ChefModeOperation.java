package interface_adapters.tui.controllers;

import entity.Recipe;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.ChefMode;
import usecase.RecipeManager;

import java.util.List;

public class ChefModeOperation implements TextualOperation{
    private Recipe recipe;

    private final ChefMode chefMode;
    private final TextualReader reader;
    private final RecipeManager recipeManager;

    private final List<TextualOperation> operations = List.of(
            new IngredientShow(),
            new NextStepShower(),
            new PreviousStepShower()
    );

    public ChefModeOperation(TextualReader reader, ChefMode chefMode, RecipeManager recipeManager) {
        this.reader = reader;
        this.chefMode = chefMode;
        this.recipeManager = recipeManager;
    }

    @Override
    public void run() {
        while (true) {
            List<Recipe> recipes = recipeManager.getAllRecipes();
            this.recipe = reader.chooseFromList(recipes, "Chef Mode Recipe");

            if (this.recipe == null) {
                break;
            }

            reader.chooseOperation(operations, "Chef Mode");
        }
    }

    @Override
    public String getDescription() {
        return "Show step-by-step recipe instructions";
    }

    @Override
    public String getCode() {
        return "chef mode";
    }

    private class IngredientShow implements TextualOperation{

        @Override
        public String getCode() {
            return "show ingredients";
        }

        @Override
        public String getDescription() {
            return "Show the ingredients required in the recipe";
        }

        @Override
        public void run() {
           System.out.printf(chefMode.showIngredients());
        }
    }


    private class NextStepShower implements TextualOperation{

        @Override
        public String getCode() {
            return "next";
        }

        @Override
        public String getDescription() {
            return "Show the next step in the recipe";
        }

        @Override
        public void run() {
            System.out.printf(chefMode.showNextStep());
        }
    }

    private class PreviousStepShower implements TextualOperation{

        @Override
        public String getCode() {
            return "previous";
        }

        @Override
        public String getDescription() {
            return "Show the previous step in the recipe";
        }

        @Override
        public void run() {
            System.out.printf(chefMode.showPreviousStep());
        }
    }

}
