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

class RecipeLikerTest {
    RecipeLiker liker;
    Dao<Recipe, String> recipes;

    @BeforeEach
    public void TestSetup() throws SQLException {
        Database database = new InMemoryDatabase();
        this.liker = new RecipeLiker(database);
        this.recipes = database.getDao(Recipe.class);
    }

    @Test
    public void RecipeLikerLikingTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 3, 120);

        this.recipes.create(pie);
        this.liker.likeRecipe(pie);

        Assertions.assertTrue(pie.getThumbsUp());
    }

    @Test
    public void RecipeLikerUnlikingTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 3, 120);
        this.recipes.create(pie);

        String result = this.liker.likeRecipe(pie);
        Assertions.assertEquals("Liked recipe successfully", result);
        Assertions.assertTrue(pie.getThumbsUp());

        String result2 = this.liker.unlikeRecipe(pie);
        Assertions.assertEquals("Unliked recipe successfully", result2);
        Assertions.assertFalse(pie.getThumbsUp());
    }

    @Test
    public void RecipeLikerGettingLikedRecipesTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe cake = new Recipe("Cake", 3, 120);

        pie.setThumbsUp(true);
        cake.setThumbsUp(false);

        List<Recipe> recipesList = List.of(pie, cake);
        this.recipes.create(recipesList);

        List<Recipe> likedRecipes = this.liker.getLikedRecipes();

        Assertions.assertEquals("Pie", likedRecipes.get(0).getName());
        Assertions.assertEquals(1, likedRecipes.size());
    }

    @Test
    public void RecipeLikerGettingUnlikedRecipesTest() throws SQLException {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe cake = new Recipe("Cake", 3, 120);

        pie.setThumbsUp(true);
        cake.setThumbsUp(false);

        List<Recipe> recipesList = List.of(pie, cake);
        this.recipes.create(recipesList);

        List<Recipe> unlikedRecipes = this.liker.getUnlikedRecipes();

        Assertions.assertEquals("Cake", unlikedRecipes.get(0).getName());
        Assertions.assertEquals(1, unlikedRecipes.size());
    }
}
