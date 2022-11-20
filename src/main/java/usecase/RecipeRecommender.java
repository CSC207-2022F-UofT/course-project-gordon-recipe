package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.Recipe;

import java.util.ArrayList;

public class RecipeRecommender {
    private final Database database;

    public RecipeRecommender(Database database) {
        this.database = database;
    }

    public Recipe getRecommendation() {
        ArrayList<Recipe> data = getRecommendationData();
        return recommendationAlgorithm(data);
    }

    private ArrayList<Recipe> getRecommendationData() {
        Dao<Recipe, String> recipes = database.getDao(Recipe.class);
        ArrayList<Recipe> recipeList = new ArrayList<>();

        for (Recipe entry : recipes) {
            recipeList.add(entry);
        }

        return recipeList;
    }

    private Recipe recommendationAlgorithm(ArrayList<Recipe> recipeList) {
        recipeList.sort((o1, o2) -> {
            int t1 = o1.getTimesCooked();
            int t2 = o2.getTimesCooked();

            return Integer.compare(t2, t1);
        });

        return recipeList.get(0);
    }
}
