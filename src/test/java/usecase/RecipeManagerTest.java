package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.Recipe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class RecipeManagerTest {
    private RecipeManager manager;
    private Dao<Recipe, String> recipes;

    @BeforeEach
    public void Setup() throws SQLException {
        Database database = new InMemoryDatabase();
        manager = new RecipeManager(database);
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
}
