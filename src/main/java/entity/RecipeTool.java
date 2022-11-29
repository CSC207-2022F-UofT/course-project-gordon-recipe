package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "recipe_tools")
public class RecipeTool implements Serializable {
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

    public RecipeTool() {

    }

    public Tool getTool() {
        return tool;
    }
}
