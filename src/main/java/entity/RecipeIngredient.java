package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "recipe_ingredients")
@SuppressWarnings("unused")
public class RecipeIngredient implements Serializable, RecipePreparationItem {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private Recipe recipe;

    @DatabaseField(foreign = true)
    private Ingredient ingredient;

    /**
     * The quantity of the ingredient, e.g. "1 cup" or "12 grams"
     */
    @DatabaseField(canBeNull = false)
    private String quantity;

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, String quantity) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public RecipeIngredient() {

    }

    @Override
    public String toString() {
        return String.format("%s %s", quantity, ingredient.getName());
    }

    @Override
    public Ingredient getPreparation() {
        return ingredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
