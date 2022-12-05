package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.ArrayList;

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

            ArrayList<Recipe> recipeList = new ArrayList<>();
            recipeList.add(pie);
            recipeList.add(lasagna);
            recipeList.add(sushi);

            Recipe recommendation = recommender.recommendationTypeA(recipeList);

            Assertions.assertEquals(pie.getName(), recommendation.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void RecommendRecipe1() {
        try {
            Database db = new InMemoryDatabase();

            RecipeRecommender recommender = new RecipeRecommender(db);

            Dao<Recipe, String> recipes = db.getDao(Recipe.class);

            Recipe pie = new Recipe("Pie", 10, 10);
            Recipe lasagna = new Recipe("Lasagna", 3, 120);
            Recipe sushi = new Recipe("Sushi", 6, 25);
            recipes.create(pie);
            recipes.create(lasagna);
            recipes.create(sushi);

            sushi.setThumbsUp(true);

            ArrayList<Recipe> recipeList = new ArrayList<>();
            recipeList.add(pie);
            recipeList.add(lasagna);
            recipeList.add(sushi);

            Recipe recommendation = recommender.recommendationTypeB(recipeList);

            Assertions.assertEquals(sushi.getName(), recommendation.getName());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
