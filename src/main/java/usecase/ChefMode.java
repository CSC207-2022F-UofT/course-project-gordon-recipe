package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.Recipe;
import entity.RecipeIngredient;
import entity.Step;

import java.sql.SQLException;
import java.util.List;

/**
 * Chef mode is a use-case which displays a list of ingredients and steps to the user.
 */
public class ChefMode {
    /**
     * The list of steps.
     */
    private final List<Step> steps;

    /**
     * The list of ingredients in the recipe.
     */
    private final List<RecipeIngredient> ingredients;

    /**
     * The number of the current step.
     */
    private int currentStep;

    /**
     * Initializes the chef mode use-case.
     *
     * @param recipe   the recipe to perform chef mode on
     * @param database the database to retrieve steps and ingredients from
     */
    public ChefMode(Recipe recipe, Database database) {
        Dao<RecipeIngredient, Integer> recipeIngredients = database.getDao(RecipeIngredient.class);
        Dao<Step, String> steps = database.getDao(Step.class);

        this.currentStep = 0;

        try {
            this.steps = steps.query(
                    steps.queryBuilder()
                            .orderBy("number", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );

            this.ingredients = recipeIngredients.query(
                    recipeIngredients.queryBuilder()
                            .orderBy("id", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the recipe's ingredients.
     *
     * @return a string of ingredients and quantity needed in the given recipe
     */
    public String showIngredients() {
        String ingredientsString = "Ingredients:";

        for (RecipeIngredient recipeIngredient : ingredients) {
            ingredientsString = ingredientsString.concat("\n" +
                    recipeIngredient.getQuantity() + " " + recipeIngredient.getIngredient().getName());
        }
        return ingredientsString;
    }

    /**
     * Returns the recipe's ingredients.
     *
     * @return a list of the recipe's ingredients
     */
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * Returns the recipe's steps.
     *
     * @return a list of the recipe's steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Returns the text in the step after the current step.
     *
     * @return a string of the text in the next ste
     */
    public String showNextStep() {
        this.currentStep += 1;

        if (this.currentStep > this.steps.size()) {
            return "There are no more steps!";
        } else {
            return this.steps.get(currentStep - 1).getText();
        }
    }

    /**
     * Returns the text in the step before the current step.
     *
     * @return a string of the text in the previous step
     */
    public String showPreviousStep() {
        this.currentStep -= 1;

        if (this.currentStep < 1) {
            return "There is no previous step!";
        } else {
            return this.steps.get(currentStep - 1).getText();
        }
    }


}
