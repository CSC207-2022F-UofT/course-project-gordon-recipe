package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.UUID;

@DatabaseTable(tableName = "steps")
public class Step implements Serializable {
    /**
     * The ID of the step.
     */
    @DatabaseField(id = true, canBeNull = false)
    private String id;

    /**
     * The step's recipe.
     */
    @DatabaseField(canBeNull = false, foreign = true)
    private Recipe recipe;

    /**
     * The text of the step itself.
     */
    @DatabaseField(canBeNull = false, width = 1500)
    private String text;

    /**
     * The order of this step in the recipe's steps.
     */
    @DatabaseField(canBeNull = false)
    private int number;

    /**
     * Instantiates a new step for a given recipe.
     *
     * @param text   the step text
     * @param number the order of the step
     * @param recipe the step's recipe
     */
    public Step(String text, int number, Recipe recipe) {
        this.id = UUID.randomUUID().toString();
        this.recipe = recipe;
        this.text = text;
        this.number = number;
    }

    /**
     * Instantiates an empty step.
     */
    public Step() {

    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
