package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearcher<T extends Preparation> {
    private final Dao<Recipe, String> recipes;
    private final Dao<RecipeIngredient, Integer> recipeIngredients;
    private final Dao<RecipeTool, String> recipeTools;
    private final Dao<RecipeTag, String> recipeTags;
    private final Dao<Ingredient, String> ingredients;
    private final Dao<Tool, String> tools;
    private final Dao<Tag, String> tags;


    public RecipeSearcher(Database database) {
        this.recipes = database.getDao(Recipe.class);
        this.recipeIngredients = database.getDao(RecipeIngredient.class);
        this.recipeTools = database.getDao(RecipeTool.class);
        this.recipeTags = database.getDao(RecipeTag.class);
        this.ingredients = database.getDao(Ingredient.class);
        this.tools = database.getDao(Tool.class);
        this.tags = database.getDao(Tag.class);
    }

    /**
     * Searches for recipes that contain certain Ingredient, Tool, or Tag.
     *
     * @param searchList List of Ingredient, Tool, or Tag to search in the recipes
     * @return List of Recipe that contains all elements in searchList
     */
    public List<Recipe> searchRecipe(List<T> searchList) {
        List<Recipe> returnRecipe = new ArrayList<>();

        for (T prep : searchList) {

            // adds "recipe" that contains "prep" to "returnRecipe"
            for (Recipe recipe : recipes) {
                List<T> recipePreparation = searchPreparation(recipe, prep);
                for (T preparation : recipePreparation) {
                    if (preparation.equals(prep) && !hasRecipe(returnRecipe, recipe)) {
                        returnRecipe.add(recipe);
                    }
                }
            }

        }

        List<Recipe> tempRecipe = new ArrayList<>(returnRecipe);

        for (T prep : searchList) {
            for (Recipe recipe : tempRecipe) {
                List<T> recipePreparation = searchPreparation(recipe, prep);
                boolean hasPreparation = false;
                for (T preparation : recipePreparation) {
                    if (preparation.equals(prep)) {
                        hasPreparation = true;
                        break;
                    }
                }
                if (!hasPreparation) {
                    returnRecipe.remove(recipe);
                }
            }
        }

        return returnRecipe;
    }


    /**
     * Searches for RecipePreparation that are in a recipe and have same class as prep.
     *
     * @param recipe The recipe to search Preparations from
     * @param prep   The Preparation entity used to determine whether to search
     *               for Ingredients, Tools, or Tags
     * @param <T2>   Classes under RecipePreparation: RecipeIngredient, RecipeTool, or RecipeTag
     * @return List of RecipePreparation that are in the recipe and are same class as prep.
     */
    private <T2 extends RecipePreparation> List<T> searchPreparation(Recipe recipe, T prep) {
        List<T> preparationList = new ArrayList<>();
        List<T2> prepList = new ArrayList<>();

        try {
            if (prep instanceof Ingredient) {
                prepList = (List<T2>) recipeIngredients.query(
                        recipeIngredients.queryBuilder()
                                .where().eq("recipe_id", recipe.getID())
                                .prepare()
                );
            } else if (prep instanceof Tool) {
                prepList = (List<T2>) recipeTools.query(
                        recipeTools.queryBuilder()
                                .where().eq("recipe_id", recipe.getID())
                                .prepare()
                );
            } else if (prep instanceof Tag) {
                prepList = (List<T2>) recipeTags.query(
                        recipeTags.queryBuilder()
                                .where().eq("recipe_id", recipe.getID())
                                .prepare()
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (T2 recipePreparation : prepList) {
            preparationList.add((T) recipePreparation.getPreparation());
        }
        return preparationList;
    }

    /**
     * Checks whether recipeList contains recipe.
     *
     * @param recipeList List of Recipes
     * @param recipe     Recipe to check if it is in the list
     * @return True iff the recipe is in the list
     */
    private boolean hasRecipe(List<Recipe> recipeList, Recipe recipe) {
        for (Recipe r : recipeList) {
            if (recipe.equals(r)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Checks if any item in the database has the certain name and type
     * and returns it.
     *
     * @param str The name of the Preparation
     * @param typ The type of the Preparation
     * @return The Preparation entity that has the name and type
     */
    public T inDatabase(String str, String typ) {
        switch (typ) {
            case "Ingredient":
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getName().equals(str)) {
                        return (T) ingredient;
                    }
                }
                break;
            case "Tool":
                for (Tool tool : tools) {
                    if (tool.getName().equals(str)) {
                        return (T) tool;
                    }
                }
                break;
            case "Tag":
                for (Tag tag : tags) {
                    if (tag.getName().equals(str)) {
                        return (T) tag;
                    }
                }
                break;
        }

        return null;
    }


}
