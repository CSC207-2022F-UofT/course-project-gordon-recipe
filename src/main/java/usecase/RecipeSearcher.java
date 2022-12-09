package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearcher {
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
     * Searches through the Recipe database to find recipes
     * that contain certain Ingredient, Tool, or Tag.
     *
     * @param searchList List of Ingredient, Tool, or Tag to search in the recipes
     * @return List of Recipe that contains all elements in searchList
     */
    public List<Recipe> searchRecipe(List<PreparationItem> searchList) {
        List<Recipe> returnRecipe = new ArrayList<>();

        for (PreparationItem prep : searchList) {

            // adds "recipe" that contains "prep" to "returnRecipe"
            for (Recipe recipe : recipes) {
                List<PreparationItem> recipePreparation = searchPreparation(recipe, prep);
                for (PreparationItem preparation : recipePreparation) {
                    if (preparation.equals(prep) && !hasRecipe(returnRecipe, recipe)) {
                        returnRecipe.add(recipe);
                    }
                }
            }

        }

        List<Recipe> tempRecipe = new ArrayList<>(returnRecipe);

        for (PreparationItem prep : searchList) {
            for (Recipe recipe : tempRecipe) {
                List<PreparationItem> recipePreparation = searchPreparation(recipe, prep);
                boolean hasPreparation = false;
                for (PreparationItem preparation : recipePreparation) {
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
     * Searches for RecipePreparationItem in a recipe and
     * if the RecipePreparation linked to RecipePreparationItem have same class as prep,
     * adds it to a list and returns the list of RecipePreparation.
     *
     * @param recipe The recipe to search Preparations from
     * @param prep   The Preparation entity used to determine whether to search
     *               for Ingredients, Tools, or Tags
     * @param <R>    Classes under RecipePreparationItem: RecipeIngredient, RecipeTool, or RecipeTag
     * @return List of RecipePreparation that are in the recipe and are same class as prep.
     */
    @SuppressWarnings("unchecked")
    private <R extends RecipePreparationItem> List<PreparationItem>
    searchPreparation(Recipe recipe, PreparationItem prep) {
        List<PreparationItem> preparationList = new ArrayList<>();
        List<R> prepList = new ArrayList<>();

        try {
            if (prep instanceof Ingredient) {
                prepList = (List<R>) recipeIngredients.query(
                        recipeIngredients.queryBuilder()
                                .where().eq("recipe_id", recipe.getID())
                                .prepare()
                );
            } else if (prep instanceof Tool) {
                prepList = (List<R>) recipeTools.query(
                        recipeTools.queryBuilder()
                                .where().eq("recipe_id", recipe.getID())
                                .prepare()
                );
            } else if (prep instanceof Tag) {
                prepList = (List<R>) recipeTags.query(
                        recipeTags.queryBuilder()
                                .where().eq("recipe_id", recipe.getID())
                                .prepare()
                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (RecipePreparationItem recipePreparation : prepList) {
            preparationList.add(recipePreparation.getPreparationItem());
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
     * and returns it. null is returned when there is no item satisfying the criteria.
     *
     * @param str  The name of the Preparation
     * @param kind The name of class of Preparation, ex: "Ingredient"
     * @return The Preparation entity that has the name and type
     */
    public PreparationItem inDatabase(String str, String kind) {
        switch (kind) {
            case "Ingredient":
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getName().equals(str)) {
                        return ingredient;
                    }
                }
                break;
            case "Tool":
                for (Tool tool : tools) {
                    if (tool.getName().equals(str)) {
                        return tool;
                    }
                }
                break;
            case "Tag":
                for (Tag tag : tags) {
                    if (tag.getName().equals(str)) {
                        return tag;
                    }
                }
                break;
        }

        return null;
    }
}
