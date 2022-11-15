package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    /**
     * The connection to the database.
     */
    private final Connection connection;

    /**
     * The ORMLite connection source for creating DAOs.
     */
    private final ConnectionSource connectionSource;

    /**
     * Recipes DAO.
     */
    private final Dao<Recipe, String> recipes;

    /**
     * RecipeTools DAO.
     */
    private final Dao<RecipeTool, Integer> recipeTools;
    /**
     * RecipeIngredients DAO.
     */
    private final Dao<RecipeIngredient, Integer> recipeIngredients;
    /**
     * RecipeTags DAO.
     */
    private final Dao<RecipeTag, Integer> recipeTags;

    /**
     * Tools DAO.
     */
    private final Dao<Tool, String> tools;
    /**
     * Ingredients DAO.
     */
    private final Dao<Ingredient, String> ingredients;
    /**
     * Tags DAO.
     */
    private final Dao<Tag, String> tags;
    /**
     * Notes DAO.
     */
    private final Dao<Note, String> notes;
    /**
     * Steps DAO.
     */
    private final Dao<Step, String> steps;

    /**
     * Initializes a new database from a URL to a persisted or in memory database.
     *
     * @param url the URL pointing to the database
     */
    public Database(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
        this.connectionSource = new JdbcConnectionSource(url);

        this.recipes = DaoManager.createDao(connectionSource, Recipe.class);

        this.recipeTools = DaoManager.createDao(connectionSource, RecipeTool.class);
        this.recipeIngredients = DaoManager.createDao(connectionSource, RecipeIngredient.class);
        this.recipeTags = DaoManager.createDao(connectionSource, RecipeTag.class);

        this.tools = DaoManager.createDao(connectionSource, Tool.class);
        this.ingredients = DaoManager.createDao(connectionSource, Ingredient.class);
        this.tags = DaoManager.createDao(connectionSource, Tag.class);
        this.notes = DaoManager.createDao(connectionSource, Note.class);
        this.steps = DaoManager.createDao(connectionSource, Step.class);
    }

    public Connection getConnection() {
        return connection;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public Dao<Recipe, String> getRecipes() {
        return recipes;
    }

    public Dao<Tool, String> getTools() {
        return tools;
    }

    public Dao<RecipeTool, Integer> getRecipeTools() {
        return recipeTools;
    }

    public Dao<RecipeIngredient, Integer> getRecipeIngredients() {
        return recipeIngredients;
    }

    public Dao<RecipeTag, Integer> getRecipeTags() {
        return recipeTags;
    }

    public Dao<Note, String> getNotes() {
        return notes;
    }

    public Dao<Ingredient, String> getIngredients() {
        return ingredients;
    }

    public Dao<Tag, String> getTags() {
        return tags;
    }

    public Dao<Step, String> getSteps() {
        return steps;
    }
}
