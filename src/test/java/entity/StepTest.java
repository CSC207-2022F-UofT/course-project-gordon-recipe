package entity;

import com.j256.ormlite.dao.Dao;
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

        Dao<Recipe, String> recipes = db.getDao(Recipe.class);
        Dao<Step, String> steps = db.getDao(Step.class);

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


    @Test
    public void IDTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        Step step1 = new Step("blend fruit", 1, smoothie);

        step1.setID("000");

        Assertions.assertEquals(step1.getID(), "000");
    }

    @Test
    public void SetRecipeTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        Recipe fruitSoup = new Recipe("Fruit Soup", 1, 1);
        Step step1 = new Step("blend fruit", 1, smoothie);

        step1.setRecipe(fruitSoup);

        Assertions.assertEquals(step1.getRecipe(), fruitSoup);

    }

    @Test
    public void SetTextTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        Step step1 = new Step("blend fruit", 1, smoothie);

        step1.setText("blend fruit and ice cubes");

        Assertions.assertEquals(step1.getText(), "blend fruit and ice cubes");
    }

    @Test
    public void SetNumberTest() {
        Recipe smoothie = new Recipe("smoothie", 1, 1);
        Step step1 = new Step("blend fruit", 1, smoothie);

        step1.setNumber(0);

        Assertions.assertEquals(step1.getNumber(), 0);
    }

}
