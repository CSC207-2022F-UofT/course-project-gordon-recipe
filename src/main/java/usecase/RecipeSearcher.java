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

    @SuppressWarnings("unchecked")
    private <R extends RecipePreparationItem> List<PreparationItem> searchPreparation(Recipe recipe, PreparationItem prep) {
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
            preparationList.add(recipePreparation.getPreparation());
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
