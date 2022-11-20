package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * RecipeManager is a use-case for managing recipes
 */
public class RecipeManager {
    private final Dao<Recipe, Object> recipes;

    /**
     * Initializes a new recipe manager.
     *
     * @param database the database to manage recipes in
     */
    public RecipeManager(Database database) {
        this.recipes = database.getDao(Recipe.class);
    }

    /**
     * Stores a recipe in the database.
     *
     * @param recipe the recipe to store
     */
    public void createRecipe(Recipe recipe) {
        try {
            recipes.create(recipe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a recipe in the database.
     *
     * @param recipe the recipe to update
     */
    public void updateRecipe(Recipe recipe) {
        try {
            recipes.update(recipe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes a recipe in the database.
     *
     * @param recipe the recipe to delete
     */
    public void deleteRecipe(Recipe recipe) {
        try {
            recipes.delete(recipe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of all recipes in the database.
     *
     * @return all recipes
     */
    public List<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        for (Recipe recipe : recipes) {
            recipeList.add(recipe);
        }

        return recipeList;
    }
}
