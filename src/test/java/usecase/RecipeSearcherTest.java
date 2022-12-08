package usecase;

import com.j256.ormlite.dao.Dao;
import database.Database;
import database.InMemoryDatabase;
import entity.*;
import interactor.RecipeInteractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearcherTest {

    private RecipeInteractor manager;
    private RecipeSearcher searcher;
    private Dao<RecipeIngredient, Integer> recipeIngredients;

    private Dao<RecipeTool, Integer> recipeTools;

    private Dao<RecipeTag, Integer> recipeTags;


    @BeforeEach
    public void Setup() throws SQLException {
        Database database = new InMemoryDatabase();
        manager = new RecipeInteractor(database);
        searcher = new RecipeSearcher(database);
        recipeIngredients = database.getDao(RecipeIngredient.class);
        recipeTools = database.getDao(RecipeTool.class);
        recipeTags = database.getDao(RecipeTag.class);
    }


    @Test
    public void SearchRecipeTestOneIngredient() {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe tomatoSpaghetti = new Recipe("Tomato Spaghetti", 2, 20);
        Recipe steak = new Recipe("Steak", 1, 15);

        manager.createRecipe(pie);
        manager.createRecipe(tomatoSpaghetti);
        manager.createRecipe(steak);

        Ingredient beefSirloin = new Ingredient("Beef Sirloin");

        RecipeIngredient steakIngredient = new RecipeIngredient(steak, beefSirloin, "200g");
        try {
            recipeIngredients.create(steakIngredient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<PreparationItem> ingredientList = new ArrayList<>();
        ingredientList.add(beefSirloin);

        List<Recipe> returnRecipe = searcher.searchRecipe(ingredientList);
        List<Recipe> expected = new ArrayList<>();
        expected.add(steak);

        Assertions.assertEquals(expected, returnRecipe);
    }

    @Test
    public void SearchRecipeTestOneTool() {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe tomatoSpaghetti = new Recipe("Tomato Spaghetti", 2, 20);
        Recipe steak = new Recipe("Steak", 1, 15);

        manager.createRecipe(pie);
        manager.createRecipe(tomatoSpaghetti);
        manager.createRecipe(steak);

        Tool pan = new Tool("Pan");

        RecipeTool steakTool = new RecipeTool(steak, pan);
        try {
            recipeTools.create(steakTool);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<PreparationItem> toolList = new ArrayList<>();
        toolList.add(pan);

        List<Recipe> returnRecipe = searcher.searchRecipe(toolList);
        List<Recipe> expected = new ArrayList<>();
        expected.add(steak);

        Assertions.assertEquals(expected, returnRecipe);
    }

    @Test
    public void SearchRecipeTestOneTag() {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe tomatoSpaghetti = new Recipe("Tomato Spaghetti", 2, 20);
        Recipe steak = new Recipe("Steak", 1, 15);

        manager.createRecipe(pie);
        manager.createRecipe(tomatoSpaghetti);
        manager.createRecipe(steak);

        Tag beef = new Tag("Beef");

        RecipeTag steakTag = new RecipeTag(steak, beef);
        try {
            recipeTags.create(steakTag);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<PreparationItem> tagList = new ArrayList<>();
        tagList.add(beef);

        List<Recipe> returnRecipe = searcher.searchRecipe(tagList);
        List<Recipe> expected = new ArrayList<>();
        expected.add(steak);

        Assertions.assertEquals(expected, returnRecipe);
    }

    @Test
    public void SearchRecipeTestManyRecipes() {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe tomatoSpaghetti = new Recipe("Tomato Spaghetti", 2, 20);
        Recipe steak = new Recipe("Steak", 1, 15);
        Recipe tomatoJuice = new Recipe("Tomato Juice", 1, 5);
        Recipe friedTomatoEgg = new Recipe("Stir Fried Tomato and Egg", 2, 10);


        manager.createRecipe(pie);
        manager.createRecipe(tomatoSpaghetti);
        manager.createRecipe(steak);
        manager.createRecipe(tomatoJuice);
        manager.createRecipe(friedTomatoEgg);

        Ingredient tomato = new Ingredient("Tomato");

        RecipeIngredient tomatoTomatoSpaghetti = new RecipeIngredient(tomatoSpaghetti, tomato, "2");
        RecipeIngredient tomatoTomatoJuice = new RecipeIngredient(tomatoJuice, tomato, "3");
        RecipeIngredient tomatoFriedTomatoEgg = new RecipeIngredient(friedTomatoEgg, tomato, "3");
        try {
            recipeIngredients.create(tomatoTomatoSpaghetti);
            recipeIngredients.create(tomatoTomatoJuice);
            recipeIngredients.create(tomatoFriedTomatoEgg);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<PreparationItem> ingredientList = new ArrayList<>();
        ingredientList.add(tomato);

        List<Recipe> returnRecipe = searcher.searchRecipe(ingredientList);
        List<Recipe> expected = new ArrayList<>();
        expected.add(tomatoSpaghetti);
        expected.add(tomatoJuice);
        expected.add(friedTomatoEgg);

        Assertions.assertEquals(expected, returnRecipe);
    }

    @Test
    public void SearchRecipeTestManyIngredients() {
        Recipe pie = new Recipe("Pie", 3, 120);
        Recipe tomatoSpaghetti = new Recipe("Tomato Spaghetti", 2, 20);
        Recipe steak = new Recipe("Steak", 1, 15);
        Recipe tomatoJuice = new Recipe("Tomato Juice", 1, 5);
        Recipe friedTomatoEgg = new Recipe("Stir Fried Tomato and Egg", 2, 10);


        manager.createRecipe(pie);
        manager.createRecipe(tomatoSpaghetti);
        manager.createRecipe(steak);
        manager.createRecipe(tomatoJuice);
        manager.createRecipe(friedTomatoEgg);

        Ingredient tomato = new Ingredient("Tomato");
        Ingredient pasta = new Ingredient("Pasta");

        RecipeIngredient tomatoTomatoSpaghetti = new RecipeIngredient(tomatoSpaghetti, tomato, "2");
        RecipeIngredient tomatoTomatoJuice = new RecipeIngredient(tomatoJuice, tomato, "3");
        RecipeIngredient tomatoFriedTomatoEgg = new RecipeIngredient(friedTomatoEgg, tomato, "3");
        RecipeIngredient pastaTomatoSpaghetti = new RecipeIngredient(tomatoSpaghetti, pasta, "150g");
        try {
            recipeIngredients.create(tomatoTomatoSpaghetti);
            recipeIngredients.create(tomatoTomatoJuice);
            recipeIngredients.create(tomatoFriedTomatoEgg);
            recipeIngredients.create(pastaTomatoSpaghetti);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        List<PreparationItem> ingredientList = new ArrayList<>();
        ingredientList.add(tomato);
        ingredientList.add(pasta);

        List<Recipe> returnRecipe = searcher.searchRecipe(ingredientList);
        List<Recipe> expected = new ArrayList<>();
        expected.add(tomatoSpaghetti);

        Assertions.assertEquals(expected, returnRecipe);
    }

}
