package interactor;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RecipeManager is an interactor for managing recipes.
 */
@SuppressWarnings("SpellCheckingInspection")
public class RecipeInteractor {
    private final Dao<Recipe, Object> recipes;
    private final Database database;

    /**
     * Initializes a new recipe interactor.
     *
     * @param database the database to manage recipes in
     */
    public RecipeInteractor(Database database) {
        this.database = database;
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
     * Creates and stores tags with a recipe.
     *
     * @param recipe The recipe to attach tags to
     * @param tags   The tags to attach to the recipe
     */
    @SuppressWarnings("UnusedReturnValue")
    public List<RecipeTag> createRecipeTags(Recipe recipe, List<Tag> tags) {
        Dao<Tag, String> tagsDao = database.getDao(Tag.class);
        Dao<RecipeTag, Integer> recipeTagsDao = database.getDao(RecipeTag.class);

        List<RecipeTag> recipeTags = new ArrayList<>();

        for (Tag tag : tags) {
            RecipeTag recipeTag = new RecipeTag(recipe, tag);
            recipeTags.add(recipeTag);

            try {
                tagsDao.createIfNotExists(tag);
                recipeTagsDao.createIfNotExists(recipeTag);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return recipeTags;
    }

    /**
     * Creates and stores tools with a recipe.
     *
     * @param recipe The recipe to attach tools to
     * @param tools  The tools to attach to the recipe
     */
    @SuppressWarnings("UnusedReturnValue")
    public List<RecipeTool> createRecipeTools(Recipe recipe, List<Tool> tools) {
        Dao<Tool, String> toolsDao = database.getDao(Tool.class);
        Dao<RecipeTool, Integer> recipeToolsDao = database.getDao(RecipeTool.class);

        List<RecipeTool> recipeTools = new ArrayList<>();

        for (Tool tool : tools) {
            RecipeTool recipeTool = new RecipeTool(recipe, tool);
            recipeTools.add(recipeTool);

            try {
                toolsDao.createIfNotExists(tool);
                recipeToolsDao.createIfNotExists(recipeTool);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return recipeTools;
    }

    /**
     * Creates and stores recipe ingredients.
     *
     * @param recipeIngredients The recipe ingredients to store
     */
    public void createRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        Dao<Ingredient, String> ingredientsDao = database.getDao(Ingredient.class);
        Dao<RecipeIngredient, Integer> recipeIngredientsDao = database.getDao(RecipeIngredient.class);

        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            try {
                ingredientsDao.createIfNotExists(recipeIngredient.getPreparationItem());
                recipeIngredientsDao.createIfNotExists(recipeIngredient);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates and stores steps.
     *
     * @param steps The steps to save
     */
    public void createRecipeSteps(List<Step> steps) {
        Dao<Step, String> stepsDao = database.getDao(Step.class);

        try {
            stepsDao.create(steps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of all recipes in the database.
     *
     * @return all recipes
     */
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        for (Recipe recipe : recipes) {
            recipeList.add(recipe);
        }

        return recipeList;
    }

    /**
     * Returns a list of tools in the given recipe.
     *
     * @return the tools
     */
    public List<Tool> getTools(Recipe recipe) {
        Dao<RecipeTool, Integer> recipeToolsDao = database.getDao(RecipeTool.class);

        try {
            Stream<RecipeTool> recipeTools = recipeToolsDao.query(
                    recipeToolsDao.queryBuilder()
                            .orderBy("tool_id", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            ).stream();

            return recipeTools.map(RecipeTool::getPreparationItem).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of recipe ingredients in the given recipe.
     *
     * @return the recipe ingredients
     */
    public List<RecipeIngredient> getRecipeIngredients(Recipe recipe) {
        Dao<RecipeIngredient, Integer> recipeIngredientsDao = database.getDao(RecipeIngredient.class);

        try {
            return recipeIngredientsDao.query(
                    recipeIngredientsDao.queryBuilder()
                            .orderBy("ingredient_id", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a recipe's steps
     *
     * @param recipe the recipe to get the steps for
     * @return the steps of the recipe
     */
    public List<Step> getSteps(Recipe recipe) {
        Dao<Step, String> steps = database.getDao(Step.class);

        try {
            return steps.query(
                    steps.queryBuilder()
                            .orderBy("number", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a recipe's notes
     *
     * @param recipe the recipe to get the notes for
     * @return the notes of the recipe
     */
    public List<Note> getNotes(Recipe recipe) {
        Dao<Note, String> notes = database.getDao(Note.class);

        try {
            return notes.query(
                    notes.queryBuilder()
                            .orderBy("date", true)
                            .where().eq("recipe_id", recipe.getID())
                            .prepare()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
