package entity;

import java.util.UUID;

public class Step {
    /**
     * The ID of the step.
     */
    private String id;

    /**
     * The ID of the step's recipe.
     */
    private String recipeID;

    /**
     * The text of the step itself.
     */
    private String text;

    /**
     * The order of this step in the recipe's steps.
     */
    private int number;

    /**
     * Instantiates a new step for a given recipe.
     *
     * @param text     the step text
     * @param number   the order of the step
     * @param recipeID the ID of the step's recipe
     */
    public Step(String text, int number, String recipeID) {
        this.id = UUID.randomUUID().toString();
        this.recipeID = recipeID;
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

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
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
