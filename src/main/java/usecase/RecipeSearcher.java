package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearcher<T> {
    private final Dao<Recipe, String> recipes;
    private final Dao<RecipeIngredient, Integer> recipeIngredients;
    private final Dao<RecipeTool, String> recipeTools;
    private final Dao<RecipeTag, String> recipeTags;


    public RecipeSearcher(Database database) {
        this.recipes = database.getDao(Recipe.class);
        this.recipeIngredients = database.getDao(RecipeIngredient.class);
        this.recipeTools = database.getDao(RecipeTool.class);
        this.recipeTags = database.getDao(RecipeTag.class);
    }

    public List<Recipe> searchRecipe(List<T> searchList) {
        ArrayList<Recipe> returnRecipe = new ArrayList<>();

        for (T prep: searchList) {

            // adds "recipe" that contains "prep" to "returnRecipe"
            for (Recipe recipe: recipes) {
                if (prep instanceof Ingredient) {
                    List<Ingredient> recipeIngredient = searchIngredient(recipe);
                    for (Ingredient ingredient: recipeIngredient) {
                        if (ingredient.equals((Ingredient) prep)){
                            returnRecipe.add(recipe);
                        }
                    }

                } else if (prep instanceof Tool) {
                    List<Tool> recipeTool = searchTool(recipe);
                    for (Tool tool: recipeTool) {
                        if (tool.equals((Tool) prep)){
                            returnRecipe.add(recipe);
                        }
                    }

                } else if (prep instanceof Tag) {
                    List<Tag> recipeTag = searchTag(recipe);
                    for (Tag tag: recipeTag) {
                        if (tag.equals((Tag) prep)){
                            returnRecipe.add(recipe);
                        }
                    }
                }

            }

//            // removes "recipe" from "returnRecipe" if it doesn't contain "prep"
//            for (Recipe recipe: returnRecipe) {
//                if (prep instanceof Ingredient) {
//                    if (!searchIngredient(recipe).contains(prep)) {
//                        returnRecipe.remove(recipe);
//                    }
//                } else if (prep instanceof Tool) {
//                    if (!searchTool(recipe).contains(prep)) {
//                        returnRecipe.remove(recipe);
//                    }
//                } else if (prep instanceof Tag) {
//                    if (!searchTag(recipe).contains(prep)) {
//                        returnRecipe.remove(recipe);
//                    }
//                }
//            }

        }

        return returnRecipe;
    }

    private List<Ingredient> searchIngredient(Recipe recipe) {
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            List<RecipeIngredient> prepList = recipeIngredients.query(
                    recipeIngredients.queryBuilder()
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );

            for (RecipeIngredient recipeIngredient: prepList) {
                ingredientList.add(recipeIngredient.getIngredient());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredientList;
    }

    private List<Tool> searchTool(Recipe recipe) {
        List<Tool> toolList = new ArrayList<>();
        try {
            List<RecipeTool> prepList = recipeTools.query(
                    recipeTools.queryBuilder()
                            .orderBy("id", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );

            for (RecipeTool recipeTool: prepList) {
                toolList.add(recipeTool.getTool());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toolList;
    }

    private List<Tag> searchTag(Recipe recipe) {
        List<Tag> tagList = new ArrayList<>();
        try {
            List<RecipeTag> prepList = recipeTags.query(
                    recipeTags.queryBuilder()
                            .orderBy("id", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );

            for (RecipeTag recipeTag: prepList) {
                tagList.add(recipeTag.getTag());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tagList;
    }

}
