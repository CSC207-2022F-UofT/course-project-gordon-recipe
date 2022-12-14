package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * A helper class that ties a recipe to a tool.
 */
@DatabaseTable(tableName = "recipe_tools")
@SuppressWarnings("unused")
public class RecipeTool implements Serializable, RecipePreparationItem {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private Recipe recipe;

    @DatabaseField(foreign = true)
    private Tool tool;

    public RecipeTool(Recipe recipe, Tool tool) {
        this.recipe = recipe;
        this.tool = tool;
    }

    public RecipeTool(Recipe recipe, Tool tool, int id) {
        this.recipe = recipe;
        this.tool = tool;
        this.id = id;
    }

    public RecipeTool() {

    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getID() {
        return id;
    }

    @Override
    public Tool getPreparationItem() {
        return tool;
    }
}
