package entity;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import database.Database;
import database.InMemoryDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class StepTest {
    @Test
    public void StoredStep() throws SQLException {
        Database db = new InMemoryDatabase();

        Dao<Recipe, String> recipes = db.getRecipes();
        Dao<Step, String> steps = db.getSteps();

        TableUtils.createTable(recipes);
        TableUtils.createTable(steps);

        Recipe omelette = new Recipe("Scrambled Eggs", 1, 10);

        List<Step> omeletteSteps = List.of(
                new Step("Break the eggs into a bowl", 1, omelette),
                new Step("Whisk the eggs", 2, omelette)
        );

        recipes.create(omelette);
        steps.create(omeletteSteps);

        List<Step> retrievedSteps = steps.query(
                steps.queryBuilder()
                        .orderBy("number", true)
                        .where().eq("recipe_id", omelette.getID())
                        .prepare()
        );

        Assertions.assertEquals(2, retrievedSteps.size());
        Assertions.assertEquals(omeletteSteps.get(0).getText(), retrievedSteps.get(0).getText());
        Assertions.assertEquals(omeletteSteps.get(1).getText(), retrievedSteps.get(1).getText());
    }
}
