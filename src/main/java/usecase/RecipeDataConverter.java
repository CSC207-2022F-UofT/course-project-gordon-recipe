package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Facilitates import/export functionality by performing conversion
 * from a group of objects in a given database to a RecipeData object,
 * or vice versa.
 */
public class RecipeDataConverter {
    private final Dao<Recipe, String> recipeDao;
    private final Dao<RecipeIngredient, Integer> recipeIngredientDao;
    private final Dao<RecipeTag, Integer> recipeTagDao;
    private final Dao<RecipeTool, Integer> recipeToolDao;
    private final Dao<Step, String> stepDao;
    private final Dao<Note, String> noteDao;

    private final Dao<Ingredient, String> ingredientDao;

    private final Dao<Tag, String> tagDao;

    private final Dao<Tool, String> toolDao;

    /**
     * Creates a recipe data converter connected to the given database,
     * setting up the various DAOs of the recipe data converter.
     *
     * @param database The database to read from (when exporting) / write to (when importing)
     */
    public RecipeDataConverter(Database database) {
        this.recipeDao = database.getDao(Recipe.class);
        this.recipeIngredientDao = database.getDao(RecipeIngredient.class);
        this.recipeTagDao = database.getDao(RecipeTag.class);
        this.recipeToolDao = database.getDao(RecipeTool.class);
        this.stepDao = database.getDao(Step.class);
        this.noteDao = database.getDao(Note.class);
        this.ingredientDao = database.getDao(Ingredient.class);
        this.tagDao = database.getDao(Tag.class);
        this.toolDao = database.getDao(Tool.class);
    }

    /**
     * Creates a RecipeData object for the given recipe based on the objects
     * stored in the database of this recipe data converter.
     *
     * @param recipe the recipe to create a RecipeData object for --
     *               all ingredients, tags, tools, etc. in the returned
     *               RecipeData object are for this recipe
     * @return a new RecipeData object, containing the recipe
     * and all of its ingredients, tags, tools, etc.
     */
    public RecipeData exportRecipe(Recipe recipe) {
        List<RecipeIngredient> recipeIngredients;
        List<RecipeTag> recipeTags;
        List<RecipeTool> recipeTools;
        List<Step> steps;
        List<Note> notes;
        try {
            recipeIngredients = getByRecipeId(recipeIngredientDao, recipe);
            recipeTags = getByRecipeId(recipeTagDao, recipe);
            recipeTools = getByRecipeId(recipeToolDao, recipe);
            steps = getByRecipeId(stepDao, recipe);
            notes = getByRecipeId(noteDao, recipe);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new RecipeData(recipe, recipeIngredients, recipeTags, recipeTools, steps, notes);
    }

    /**
     * Creates new objects in this recipe data converter's database
     * based on the given RecipeData object
     *
     * @param recipeData the RecipeData object whose recipe, ingredients, tags, tools, etc.
     *                   will be added to the database
     */
    public void importRecipe(RecipeData recipeData) {
        try {
            recipeDao.create(recipeData.getRecipe());
            recipeIngredientDao.create(recipeData.getRecipeIngredients());
            recipeTagDao.create(recipeData.getRecipeTags());
            recipeToolDao.create(recipeData.getRecipeTools());
            stepDao.create(recipeData.getSteps());
            noteDao.create(recipeData.getNotes());
            for (RecipeIngredient recipeIngredient : recipeData.getRecipeIngredients()) {
                ingredientDao.create(recipeIngredient.getIngredient());
            }
            for (RecipeTag recipeTag : recipeData.getRecipeTags()) {
                tagDao.create(recipeTag.getTag());
            }
            for (RecipeTool recipeTool : recipeData.getRecipeTools()) {
                toolDao.create(recipeTool.getTool());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the list of items from the given DAO that belong to the given recipe.
     *
     * @param dao    a DAO for ingredients/tags/tools/etc.
     * @param recipe the recipe that all the ingredients/tags/tools/etc.
     *               in the returned list pertain to
     * @param <T>    the type of object that is being gotten
     *               (RecipeIngredient, RecipeTag, RecipeTool, etc)
     * @param <ID>   the type for the ID column for the DAO
     * @return a list that contains precisely every ingredient/tag/tool/etc.
     * from the DAO that pertains to the given recipe
     * @throws SQLException if the database query fails
     */
    private <T, ID> List<T> getByRecipeId(Dao<T, ID> dao, Recipe recipe) throws SQLException {
        return dao.query(
                dao.queryBuilder()
                        .where().eq("recipe_id", recipe.getID())
                        .prepare()
        );
    }
}
