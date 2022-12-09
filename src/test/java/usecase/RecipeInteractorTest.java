package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.*;
import interactor.RecipeInteractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class RecipeInteractorTest {
    private Database database;
    private RecipeInteractor manager;
    private Dao<Recipe, String> recipes;

    @BeforeEach
    public void Setup() throws SQLException {
        database = new InMemoryDatabase();
        manager = new RecipeInteractor(database);
        recipes = database.getDao(Recipe.class);
    }

    @Test
    public void CreatingRecipeSavingTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 3, 120);
        manager.createRecipe(pie);

        Recipe retrievedRecipe = recipes.queryForId(pie.getID());

        Assertions.assertEquals(pie.getID(), retrievedRecipe.getID());
    }

    @Test
    public void UpdatingRecipeTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 3, 120);
        manager.createRecipe(pie);

        pie.setServings(10);
        manager.updateRecipe(pie);

        Recipe retrievedRecipe = recipes.queryForId(pie.getID());

        Assertions.assertEquals(10, retrievedRecipe.getServings());
    }

    @Test
    public void DeletingRecipeTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 10, 10);

        manager.createRecipe(pie);

        Assertions.assertEquals(1, recipes.countOf());

        manager.deleteRecipe(pie);

        Assertions.assertEquals(0, recipes.countOf());
    }

    @Test
    public void GetAllRecipesTest() throws SQLException {
        Recipe r1 = new Recipe("Pie", 10, 10);
        Recipe r2 = new Recipe("Apple Crumble", 10, 30);

        List<Recipe> recipesList = List.of(r1, r2);

        recipes.create(recipesList);

        List<Recipe> retrievedRecipes = manager.getAllRecipes();

        Assertions.assertTrue(retrievedRecipes.containsAll(recipesList));
    }

    @Test
    public void GetRecipeToolsTest() throws SQLException {
        List<Tool> tools = List.of(
                new Tool("Spatula"),
                new Tool("Knife")
        );

        Dao<Tool, String> toolsDao = database.getDao(Tool.class);
        toolsDao.create(tools);

        Recipe pie = new Recipe("Pie", 10, 10);
        recipes.create(pie);

        manager.createRecipeTools(pie, tools);

        Assertions.assertEquals(2, manager.getTools(pie).size());
    }

    @Test
    public void CreateRecipeTagsTest() throws SQLException {
        Dao<Recipe, String> recipes = db.getDao(Recipe.class);

        Recipe pie = new Recipe("Pie", 10, 10);
        recipes.create(pie);

        ArrayList<Tag> tagList = new ArrayList<>();
        Tag vegan = new Tag("vegan");
        tagList.add(vegan);

        manager.createRecipeTags(pie, tagList);

        Assertions.assertEquals(manager.getTags(pie), tagList);
    }

    @Test
    public void CreateRecipeIngredientsTest() throws SQLException {
        Dao<RecipeIngredient, Integer> recipeIngredientsDao = db.getDao(RecipeIngredient.class);

        Recipe cake = new Recipe("cake", 3, 3);
            Ingredient flour = new Ingredient("flour");
            String quantity1 = "1 cup";
            RecipeIngredient cakeFlour = new RecipeIngredient(cake, flour, quantity1);
            recipeIngredientsDao.create(cakeFlour);

        List<RecipeIngredient> ingredientList = new ArrayList<>();
        ingredientList.add(cakeFlour);

        manager.createRecipeIngredients(ingredientList);

        Assertions.assertEquals(ingredientList.get(0).getRecipe(), manager.getRecipeIngredients(cake).get(0).getRecipe());

    }

    @Test
    public void CreateRecipeStepsTest() throws SQLException {
        String text1 = "Add the fruit";
        int stepNum1 = 1;
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        Step step1 = new Step(text1, stepNum1, smoothie);

        List<Step> stepList = new ArrayList<>();
        stepList.add(step1);

        manager.createRecipeSteps(stepList);

        Assertions.assertEquals(manager.getSteps(smoothie).get(0).getRecipe(), stepList.get(0).getRecipe());

    }
}
