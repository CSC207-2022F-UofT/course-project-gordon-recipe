package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

public class RecipeRecommenderTest {
    @Test
    public void RecommendedRecipe() {
        try {
            Database db = new InMemoryDatabase();

            RecipeRecommender recommender = new RecipeRecommender(db);

            Dao<Recipe, String> recipes = db.getDao(Recipe.class);

            Recipe pie = new Recipe("Pie", 10, 10);
            Recipe lasagna = new Recipe("Lasagna", 3, 120);
            Recipe sushi = new Recipe("Sushi", 6, 25);

            pie.setTimesCooked(10);
            lasagna.setTimesCooked(3);
            sushi.setTimesCooked(5);

            recipes.create(pie);
            recipes.create(lasagna);
            recipes.create(sushi);

            Recipe recommendation = recommender.getRecommendation();

            Assertions.assertEquals(pie.getName(), recommendation.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
