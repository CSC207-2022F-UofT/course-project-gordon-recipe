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
    public void RecommendationTypeATest() {
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

            // Since pie is the only recipe in the top third most cooked, it should be recommended
            Assertions.assertEquals(pie.getName(), recommendation.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void RecommendationTypeBTest() {
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

            // since sushi is the only liked recipe, it should be recommended
            Assertions.assertEquals(sushi.getName(), recommendation.getName());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void RecommendationAlgorithmTest() {
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

            pie.setThumbsUp(true);

            pie.setTimesCooked(10);
            lasagna.setTimesCooked(9);
            sushi.setTimesCooked(8);

            ArrayList<Recipe> recipeList = new ArrayList<>();
            recipeList.add(pie);
            recipeList.add(lasagna);
            recipeList.add(sushi);

            Recipe recommendation = recommender.recommendationAlgorithm(recipeList);

            // recommendation should only be pie
            Assertions.assertEquals(pie.getName(), recommendation.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void GetRecommendationTest() {
        try {
            Database db = new InMemoryDatabase();
            RecipeRecommender recommender = new RecipeRecommender(db);
            Dao<Recipe, String> recipes = db.getDao(Recipe.class);

            Recipe lasagna = new Recipe("Lasagna", 3, 120);
            recipes.create(lasagna);

            Recipe recommendation = recommender.getRecommendation();

            Assertions.assertEquals(lasagna.getName(), recommendation.getName());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

