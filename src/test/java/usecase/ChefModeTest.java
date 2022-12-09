package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.Ingredient;
import entity.Recipe;
import entity.RecipeIngredient;
import entity.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChefModeTest {
    private Database db;
    private Dao<Recipe, String> recipes;
    private Dao<Ingredient, String> ingredients;
    private Dao<RecipeIngredient, Integer> recipeIngredients;
    private Dao<Step, String> steps;
    private Recipe pie;

    @BeforeEach
    public void ChefModeTestSetup() throws SQLException {
        this.db = new InMemoryDatabase();
        this.recipes = db.getDao(Recipe.class);
        this.ingredients = db.getDao(Ingredient.class);
        this.recipeIngredients = db.getDao(RecipeIngredient.class);
        this.steps = db.getDao(Step.class);
        this.pie = new Recipe("Pie", 10, 40);
    }

    @Test
    public void ChefModeIngredientsTest() throws SQLException {
        List<Ingredient> ingredientsList = List.of(
                new Ingredient("Egg"),
                new Ingredient("Milk")
        );

        recipes.create(pie);

        for (Ingredient ingredient : ingredientsList) {
            ingredients.create(ingredient);

            RecipeIngredient pieIngredient = new RecipeIngredient(pie, ingredient, "10");
            recipeIngredients.create(pieIngredient);
        }

        ChefMode chefMode = new ChefMode(pie, db);

        Assertions.assertEquals(2, chefMode.getIngredients().size());

        Assertions.assertEquals("Ingredients:\n10 Egg\n10 Milk", chefMode.showIngredients());
    }

    @Test
    public void ChefModeStepsTest() throws SQLException {
        List<Step> stepsList = List.of(
                new Step("Put the eggs in a bowl", 1, pie),
                new Step("Put the milk into the bowl", 2, pie)
        );

        steps.create(stepsList);

        ChefMode chefMode = new ChefMode(pie, db);

        Assertions.assertEquals(2, chefMode.getSteps().size());

        Assertions.assertEquals("Put the eggs in a bowl", chefMode.showNextStep());
        Assertions.assertEquals("Put the milk into the bowl", chefMode.showNextStep());

        Assertions.assertNull(chefMode.showNextStep());

        Assertions.assertEquals("Put the eggs in a bowl", chefMode.showPreviousStep());

        Assertions.assertNull(chefMode.showPreviousStep());
    }

    @Test
    public void ChefModeInitializerTest() throws SQLException {
        Database db = new InMemoryDatabase();
        ChefMode cm = new ChefMode(db);

        Recipe sushi = new Recipe("sushi", 1, 1);
        cm.setRecipe(sushi);
        List<Step> stepList = new ArrayList<>();
        Assertions.assertEquals(cm.getSteps(), stepList);




    }

}
