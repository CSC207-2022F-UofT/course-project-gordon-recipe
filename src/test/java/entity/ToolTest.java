package entity;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.table.TableUtils;
import database.Database;
import database.InMemoryDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class ToolTest {
    @Test
    public void ForeignKeyToolAssociation() throws SQLException {
        Database db = new InMemoryDatabase();

        Dao<Recipe, String> recipes = db.getDao(Recipe.class);
        Dao<Tool, String> tools = db.getDao(Tool.class);
        Dao<RecipeTool, Integer> recipeTools = db.getDao(RecipeTool.class);

        Recipe omelette = new Recipe("Omelette", 1, 5);
        Recipe pancakes = new Recipe("Pancakes", 3, 20);
        Recipe cake = new Recipe("Cake", 8, 60);

        Tool spatula = new Tool("Spatula");
        Tool pan = new Tool("pan");

        RecipeTool omeletteSpatula = new RecipeTool(omelette, spatula);
        RecipeTool pancakeSpatula = new RecipeTool(pancakes, spatula);
        RecipeTool cakePan = new RecipeTool(cake, pan);

        recipes.create(omelette);
        recipes.create(pancakes);
        recipes.create(cake);

        tools.create(spatula);

        recipeTools.create(omeletteSpatula);
        recipeTools.create(pancakeSpatula);
        recipeTools.create(cakePan);

        // Prepare the query. SelectArg makes the query generic.
        QueryBuilder<RecipeTool, Integer> recipeToolQuery = recipeTools.queryBuilder();
        recipeToolQuery.selectColumns("recipe_id");
        recipeToolQuery.where().eq("tool_id", new SelectArg());

        // Use above RecipeTool query to query recipes
        QueryBuilder<Recipe, String> recipesQuery = recipes.queryBuilder();
        PreparedQuery<Recipe> preparedQuery = recipesQuery
                .where()
                .in("id", recipeToolQuery)
                .prepare();

        // Specify a search for recipes with a spatula tool
        preparedQuery.setArgumentHolderValue(0, spatula);

        // Run the query
        List<Recipe> spatulaRecipes = recipes.query(preparedQuery);
        Assertions.assertEquals(2, spatulaRecipes.size());
    }
}
