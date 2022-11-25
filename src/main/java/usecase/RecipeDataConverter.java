package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import entity.*;

import java.sql.SQLException;
import java.util.List;

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

    private <T, I> List<T> getByRecipeId(Dao<T, I> dao, Recipe recipe) throws SQLException {
        return dao.query(
                dao.queryBuilder()
                        .where().eq("recipe_id", recipe.getID())
                        .prepare()
        );
    }
}
