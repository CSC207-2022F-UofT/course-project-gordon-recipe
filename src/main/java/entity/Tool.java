package entity;

import java.util.UUID;

public class Tool {
    /**
     * The ID of the tool.
     */
    private String id;

    /**
     * The ID of the tool's recipe.
     */
    private String recipeID;

    /**
     * The name of the tool.
     */
    private String name;

    /**
     * Instantiates a new tool for a given recipe.
     *
     * @param name     the name of the tool
     * @param recipeID the ID of the recipe
     */
    public Tool(String name, String recipeID) {
        this.id = UUID.randomUUID().toString();
        this.recipeID = recipeID;
        this.name = name;
    }

    /**
     * Instantiates an empty tool.
     */
    public Tool() {

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
}
