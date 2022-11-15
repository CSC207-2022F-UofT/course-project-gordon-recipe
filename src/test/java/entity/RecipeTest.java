package entity;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import database.Database;
import database.InMemoryDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class RecipeTest {
    @Test
    public void StoredRecipe() throws SQLException {
        Database db = new InMemoryDatabase();

        ConnectionSource conn = db.getConnectionSource();
        Dao<Recipe, String> recipeDao = DaoManager.createDao(conn, Recipe.class);

        TableUtils.createTable(conn, Recipe.class);

        Recipe recipeToSave = new Recipe("Pasta", 2, 20);
        recipeDao.create(recipeToSave);

        String pastaRecipeID = recipeToSave.getID();
        Recipe retrievedRecipe = recipeDao.queryForId(pastaRecipeID);

        Assertions.assertEquals("Pasta", retrievedRecipe.getName());
        Assertions.assertEquals(recipeToSave.getDate(), retrievedRecipe.getDate());
    }
}
