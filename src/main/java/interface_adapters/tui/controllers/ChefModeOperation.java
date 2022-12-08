package interface_adapters.tui.controllers;

import entity.Recipe;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.ChefMode;
import interactor.RecipeInteractor;

import java.util.List;

/**
 * An operation for running the chef mode use-case.
 */
public class ChefModeOperation implements TextualOperation {
    private final ChefMode chefMode;
    private final TextualReader reader;
    private final RecipeInteractor recipeInteractor;

    private final List<TextualOperation> operations = List.of(
            new IngredientShow(),
            new NextStepShower(),
            new PreviousStepShower()
    );

    public ChefModeOperation(TextualReader reader, ChefMode chefMode, RecipeInteractor recipeInteractor) {
        this.reader = reader;
        this.chefMode = chefMode;
        this.recipeInteractor = recipeInteractor;
    }

    @Override
    public void run() {
        while (true) {
            List<Recipe> recipes = recipeInteractor.getAllRecipes();
            Recipe recipe = reader.chooseFromList(recipes, "Chef Mode Recipe");

            if (recipe == null) {
                break;
            }

            chefMode.setRecipe(recipe);
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

    private class IngredientShow implements TextualOperation {

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

    private class NextStepShower implements TextualOperation {

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
            String step = chefMode.showNextStep();

            if (step == null) {
                Colour.info("There are no more steps!");
                return;
            }

            Colour.info(step);
        }
    }

    private class PreviousStepShower implements TextualOperation {

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
            String step = chefMode.showPreviousStep();

            if (step == null) {
                Colour.info("There are no previous steps!");
                return;
            }

            Colour.info(step);
        }
    }
}
