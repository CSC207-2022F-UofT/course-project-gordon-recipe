package usecase;

import database.Database;
import database.InMemoryDatabase;
import entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class RecipeDataConverterTest {
    @Test
    public void ExportTest() throws SQLException {
        Database db = new InMemoryDatabase();

        Recipe recipe = new Recipe("apple pi", 3, 14);

        Ingredient apple = new Ingredient("apple");
        RecipeIngredient recipeApples = new RecipeIngredient(recipe, apple, "22 sevenths");

        Tag round = new Tag("round");
        RecipeTag recipeRound = new RecipeTag(recipe, round);

        Tool measuringTape = new Tool("measuring tape");
        RecipeTool recipeMeasuringTape = new RecipeTool(recipe, measuringTape);

        Step step0 = new Step("let A be an apple", 0, recipe);
        Step step1 = new Step("let C be the circumference of A", 1, recipe);
        Step step2 = new Step("let D be the diameter of A", 2, recipe);
        Step step3 = new Step("pi = C/D", 3, recipe);

        Note tauism = new Note("apple tau might be twice as tasty", recipe);
        Note iFruit = new Note("make sure the apple is a fruit, not a computer", recipe);

        db.getDao(Recipe.class).create(recipe);
        db.getDao(Ingredient.class).create(apple);
        db.getDao(RecipeIngredient.class).create(recipeApples);
        db.getDao(Tag.class).create(round);
        db.getDao(RecipeTag.class).create(recipeRound);
        db.getDao(Tool.class).create(measuringTape);
        db.getDao(RecipeTool.class).create(recipeMeasuringTape);
        db.getDao(Step.class).create(List.of(step0, step1, step2, step3));
        db.getDao(Note.class).create(List.of(tauism, iFruit));

        RecipeDataConverter converter = new RecipeDataConverter(db);
        RecipeData data = converter.exportRecipe(recipe);

        Assertions.assertEquals("apple pi", data.getRecipe().getName());
        Assertions.assertEquals(3, data.getRecipe().getServings());
        Assertions.assertEquals(14, data.getRecipe().getPrepTime());
        Assertions.assertEquals("apple", data.getRecipeIngredients().get(0).getIngredient().getName());
        Assertions.assertEquals("22 sevenths", data.getRecipeIngredients().get(0).getQuantity());
        Assertions.assertEquals("round", data.getRecipeTags().get(0).getTag().getName());
        Assertions.assertEquals("measuring tape", data.getRecipeTools().get(0).getTool().getName());
        Assertions.assertEquals("let A be an apple", data.getSteps().get(0).getText());
        Assertions.assertEquals("let C be the circumference of A", data.getSteps().get(1).getText());
        Assertions.assertEquals("let D be the diameter of A", data.getSteps().get(2).getText());
        Assertions.assertEquals("pi = C/D", data.getSteps().get(3).getText());
        Assertions.assertEquals("apple tau might be twice as tasty", data.getNotes().get(0).getText());
        Assertions.assertEquals("make sure the apple is a fruit, not a computer", data.getNotes().get(1).getText());
    }

    @Test
    public void ImportTest() throws SQLException{
        Database db = new InMemoryDatabase();

        Recipe recipe = new Recipe("apple pi", 3, 14);

        Ingredient apple = new Ingredient("apple");
        RecipeIngredient recipeApples = new RecipeIngredient(recipe, apple, "22 sevenths");

        Tag round = new Tag("round");
        RecipeTag recipeRound = new RecipeTag(recipe, round);

        Tool measuringTape = new Tool("measuring tape");
        RecipeTool recipeMeasuringTape = new RecipeTool(recipe, measuringTape);

        Step step0 = new Step("let A be an apple", 0, recipe);
        Step step1 = new Step("let C be the circumference of A", 1, recipe);
        Step step2 = new Step("let D be the diameter of A", 2, recipe);
        Step step3 = new Step("pi = C/D", 3, recipe);

        Note tauism = new Note("apple tau might be twice as tasty", recipe);
        Note iFruit = new Note("make sure the apple is a fruit, not a computer", recipe);

        RecipeDataConverter converter = new RecipeDataConverter(db);
        RecipeData data = new RecipeData(
                recipe,
                List.of(recipeApples),
                List.of(recipeRound),
                List.of(recipeMeasuringTape),
                List.of(step0, step1, step2, step3),
                List.of(tauism, iFruit)
        );
        converter.importRecipe(data);

        List<Recipe> recipes = db.getDao(Recipe.class).queryForAll();
        List<Ingredient> ingredients = db.getDao(Ingredient.class).queryForAll();
        List<RecipeIngredient> recipeIngredients = db.getDao(RecipeIngredient.class).queryForAll();
        List<Tag> tags = db.getDao(Tag.class).queryForAll();
        List<RecipeTag> recipeTags = db.getDao(RecipeTag.class).queryForAll();
        List<Tool> tools = db.getDao(Tool.class).queryForAll();
        List<RecipeTool> recipeTools = db.getDao(RecipeTool.class).queryForAll();
        List<Step> steps = db.getDao(Step.class).queryForAll();
        List<Note> notes = db.getDao(Note.class).queryForAll();

        Assertions.assertEquals("apple pi", recipes.get(0).getName());
        Assertions.assertEquals("apple", ingredients.get(0).getName());
        Assertions.assertEquals("apple", recipeIngredients.get(0).getIngredient().getName());
        Assertions.assertEquals("round", tags.get(0).getName());
        Assertions.assertEquals("round", recipeTags.get(0).getTag().getName());
        Assertions.assertEquals("measuring tape", tools.get(0).getName());
        Assertions.assertEquals("measuring tape", recipeTools.get(0).getTool().getName());
        Assertions.assertEquals("let A be an apple", steps.get(0).getText());
        Assertions.assertEquals("let C be the circumference of A", steps.get(1).getText());
        Assertions.assertEquals("let D be the diameter of A", steps.get(2).getText());
        Assertions.assertEquals("pi = C/D", steps.get(3).getText());
        Assertions.assertEquals("apple tau might be twice as tasty", notes.get(0).getText());
        Assertions.assertEquals("make sure the apple is a fruit, not a computer", notes.get(1).getText());
    }
}
