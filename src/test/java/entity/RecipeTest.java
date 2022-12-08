package entity;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class RecipeTest {
    @Test
    public void StoredRecipe() throws SQLException {
        Database db = new InMemoryDatabase();

        Dao<Recipe, String> recipeDao = db.getDao(Recipe.class);

        Recipe recipeToSave = new Recipe("Pasta", 2, 20);
        recipeDao.create(recipeToSave);

        String pastaRecipeID = recipeToSave.getID();
        Recipe retrievedRecipe = recipeDao.queryForId(pastaRecipeID);

        Assertions.assertEquals("Pasta", retrievedRecipe.getName());
        Assertions.assertEquals(recipeToSave.getDate(), retrievedRecipe.getDate());
    }

    @Test
    public void HashCodeTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        Object hash = smoothie.hashCode();

        Assertions.assertEquals(smoothie.hashCode(), hash);
    }

    @Test
    public void ToStringTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);

        Assertions.assertEquals(smoothie.toString(), "smoothie");
    }

    @Test
    public void SetIDTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);

        smoothie.setID("7");

        Assertions.assertEquals(smoothie.getID(), "7");
    }

    @Test
    public void SetNameTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        smoothie.setName("Pineapple Smoothie");

        Assertions.assertEquals(smoothie.getName(), "Pineapple Smoothie");
    }

    @Test
    public void SetPrepTimeTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        smoothie.setPrepTime(7);

        Assertions.assertEquals(smoothie.getPrepTime(), 7);
    }

    @Test
    public void SetDateTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        LocalDateTime time = LocalDateTime.now();

        smoothie.setDate(time);

        Assertions.assertEquals(smoothie.getDate(), time);
    }

}
