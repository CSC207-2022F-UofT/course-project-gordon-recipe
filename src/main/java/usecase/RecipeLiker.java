package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * RecipeLiker is a use-case for liking, unliking and retrieving recipes based on liked status.
 */
public class RecipeLiker {
    /**
     * The DAO for recipes.
     */
    private final Dao<Recipe, Object> recipes;

    /**
     * Instantiates a new RecipeLiker.
     *
     * @param database the database to access recipes in
     */
    public RecipeLiker(Database database) {
        this.recipes = database.getDao(Recipe.class);
    }


    /**
     * Likes a recipe
     *
     * @param recipe the recipe to like
     * @return a string with an error or success message
     */
    public String likeRecipe(Recipe recipe) {
        recipe.setThumbsUp(true);

        try {
            recipes.update(recipe);
        } catch (SQLException e) {
            return "Could not like recipe";
        }

        return "Liked recipe successfully";
    }

    /**
     * Unlikes a recipe
     *
     * @param recipe the recipe to unlike
     * @return a string with an error or success message
     */
    public String unlikeRecipe(Recipe recipe) {
        recipe.setThumbsUp(false);

        try {
            recipes.update(recipe);
        } catch (SQLException e) {
            return "Could not unlike recipe";
        }

        return "Unliked recipe successfully";
    }

    /**
     * Returns a list of liked recipes
     *
     * @return the list of liked recipes
     */
    public List<Recipe> getLikedRecipes() {
        try {
            return recipes.query(
                    recipes.queryBuilder()
                            .where().eq("thumbsUp", true)
                            .prepare()
            );
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Returns a list of unliked recipes
     *
     * @return the list of unliked recipes
     */
    public List<Recipe> getUnlikedRecipes() {
        try {
            return recipes.query(
                    recipes.queryBuilder()
                            .where().eq("thumbsUp", false)
                            .prepare()
            );
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}