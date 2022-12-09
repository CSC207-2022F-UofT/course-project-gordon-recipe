package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * RecipeRecommender is a use-case for recommending recipes
 */
public class RecipeRecommender {
    private final Database database;

    /**
     * Initialize a new recipe recommender
     *
     * @param database the database to recommend recipes from
     */
    public RecipeRecommender(Database database) {
        this.database = database;
    }

    /**
     * Returns a recipe recommendation
     *
     * @return the recommended recipe
     */
    public Recipe getRecommendation() {
        ArrayList<Recipe> data = getRecommendationData();
        return recommendationAlgorithm(data);
    }


    /**
     * Goes to the database and collects all entries
     *
     * @return exhaustive list of recipes
     */
    private ArrayList<Recipe> getRecommendationData() {
        Dao<Recipe, String> recipes = database.getDao(Recipe.class);
        ArrayList<Recipe> recipeList = new ArrayList<>();

        for (Recipe entry : recipes) {
            recipeList.add(entry);
        }

        return recipeList;
    }

    /**
     * The master recommendation algorithm that calls a specific recommendation algorithm
     *
     * @param recipeList the exhaustive list of recipes
     * @return a recommended recipe
     */
    public Recipe recommendationAlgorithm(ArrayList<Recipe> recipeList) {
        int algorithmCount = 2;

        // Generate a random number to select the type of recommendation
        int algorithmChoice = ThreadLocalRandom.current().nextInt(0, algorithmCount);

        if (algorithmChoice == 0) {
            return recommendationTypeA(recipeList);
        }

        return recommendationTypeB(recipeList);
    }

    /**
     * A specific recommendation algorithm that returns a recipe based on
     * how many times it has been cooked.
     *
     * @param recipeList an exhaustive list of recipes from the database
     * @return a recommended recipe
     */
    public Recipe recommendationTypeA(List<Recipe> recipeList) {
        // make list of recipes previously cooked
        ArrayList<Recipe> previouslyMade = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getTimesCooked() > 0) {
                previouslyMade.add(recipe);
            }
        }

        // if size is zero, just recommend a random recipe
        if (previouslyMade.size() == 0) {
            Collections.shuffle(recipeList);
            return recipeList.get(0);
        }

        // if size is less than two, pick the first one
        if (previouslyMade.size() < 3) {
            return previouslyMade.get(0);
        }

        // else, sort list by times cooked
        recipeList.sort((o1, o2) -> {
            int t1 = o1.getTimesCooked();
            int t2 = o2.getTimesCooked();

            return Integer.compare(t2, t1);
        });

        // reduce to top 1/3 of most cooked
        ArrayList<Recipe> topThird = new ArrayList<>(recipeList.subList(0, recipeList.size() / 3));

        // return a random element from this list
        Collections.shuffle(topThird);
        return topThird.get(0);
    }

    /**
     * A specific recommendation algorithm that will select a liked recipe at random, or choose a random recipe
     * if no recipes have been liked
     *
     * @param recipeList the exhaustive list of recipes
     * @return a recommended recipe
     */
    public Recipe recommendationTypeB(List<Recipe> recipeList) {
        // make list of liked recipes
        ArrayList<Recipe> previouslyLiked = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getThumbsUp()) {
                previouslyLiked.add(recipe);
            }
        }

        // if size is zero, just recommend a random recipe
        if (previouslyLiked.size() == 0) {
            Collections.shuffle(recipeList);
            return recipeList.get(0);
        }

        // else, choose a random liked recipe
        Collections.shuffle(previouslyLiked);
        return previouslyLiked.get(0);
    }
}

