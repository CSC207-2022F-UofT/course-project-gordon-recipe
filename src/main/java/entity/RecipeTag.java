package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "recipe_tags")
public class RecipeTag {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private Recipe recipe;

    @DatabaseField(foreign = true)
    private Tag tag;

    public RecipeTag(Recipe recipe, Tag tag) {
        this.recipe = recipe;
        this.tag = tag;
    }

    public RecipeTag() {

    }
}
