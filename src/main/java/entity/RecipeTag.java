package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "recipe_tags")
@SuppressWarnings("unused")
public class RecipeTag implements Serializable, RecipePreparation {
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

    public RecipeTag(Recipe recipe, Tag tag, int id) {
        this.recipe = recipe;
        this.tag = tag;
        this.id = id;
    }

    public RecipeTag() {

    }

    @Override
    public Object getPreparation() {
        return tag;
    }

    public Tag getTag() {
        return tag;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getID() {
        return id;
    }
}
