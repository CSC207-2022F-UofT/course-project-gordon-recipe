package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import entity.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

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
    private Dao<Recipe, String> recipes;

    /**
     * RecipeTools DAO.
     */
    private Dao<RecipeTool, Integer> recipeTools;
    /**
     * RecipeIngredients DAO.
     */
    private Dao<RecipeIngredient, Integer> recipeIngredients;
    /**
     * RecipeTags DAO.
     */
    private Dao<RecipeTag, Integer> recipeTags;

    /**
     * Tools DAO.
     */
    private Dao<Tool, String> tools;
    /**
     * Ingredients DAO.
     */
    private Dao<Ingredient, String> ingredients;
    /**
     * Tags DAO.
     */
    private Dao<Tag, String> tags;
    /**
     * Notes DAO.
     */
    private Dao<Note, String> notes;
    /**
     * Steps DAO.
     */
    private Dao<Step, String> steps;

    /**
     * Initializes a new database from a URL to a persisted or in memory database.
     *
     * @param url the URL pointing to the database
     */
    public Database(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
        this.connectionSource = new JdbcConnectionSource(url);

        initializeDataAccessObjects();
        initializeTables();
    }

    private void initializeDataAccessObjects() throws SQLException {
        recipes = DaoManager.createDao(connectionSource, Recipe.class);
        recipeTools = DaoManager.createDao(connectionSource, RecipeTool.class);
        recipeIngredients = DaoManager.createDao(connectionSource, RecipeIngredient.class);
        recipeTags = DaoManager.createDao(connectionSource, RecipeTag.class);
        tools = DaoManager.createDao(connectionSource, Tool.class);
        ingredients = DaoManager.createDao(connectionSource, Ingredient.class);
        tags = DaoManager.createDao(connectionSource, Tag.class);
        notes = DaoManager.createDao(connectionSource, Note.class);
        steps = DaoManager.createDao(connectionSource, Step.class);
    }

    private void initializeTables() throws SQLException {
        List<Class> entities = List.of(
                Recipe.class,
                RecipeTool.class,
                RecipeIngredient.class,
                RecipeTag.class,
                Tool.class,
                Ingredient.class,
                Tag.class,
                Note.class,
                Step.class
        );

        for (Class<?> entity : entities) {
            TableUtils.createTableIfNotExists(connectionSource, entity);
        }
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
