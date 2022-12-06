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

    private boolean hasRecipe(List<Recipe> returnRecipe, Recipe recipe) {
        for (Recipe r : returnRecipe) {
            if (recipe.equals(r)) {
                return true;
            }
        }
        return false;

    }

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
