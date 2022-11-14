package entity;

import java.util.UUID;

public class Ingredient {
    /**
     * The ID of the ingredient.
     */
    private String id;

    /**
     * The ID of the ingredient's recipe.
     */
    private String recipeID;

    /**
     * The name of the ingredient.
     */
    private String name;

    /**
     * The quantity of the ingredient, e.g. "1 cup" or "12 grams".
     */
    private String quantity;

    /**
     * Instantiates a new ingredient for a given recipe.
     *
     * @param name     The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param recipeID The ID of the ingredient's recipe
     */
    public Ingredient(String name, String quantity, String recipeID) {
        this.id = UUID.randomUUID().toString();
        this.recipeID = recipeID;
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Instantiates an empty ingredient.
     */
    public Ingredient() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
