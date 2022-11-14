package entity;

import java.util.UUID;

public class Tag {
    /**
     * The ID of the tag.
     */
    private String id;

    /**
     * The ID of the tag's recipe.
     */
    private String recipeID;

    /**
     * The name of the tag.
     */
    private String name;

    /**
     * Instantiates a new tag for a given recipe.
     *
     * @param name     the name of the tag
     * @param recipeID the ID of the recipe
     */
    public Tag(String name, String recipeID) {
        this.id = UUID.randomUUID().toString();
        this.recipeID = recipeID;
        this.name = name;
    }

    /**
     * Instantiates an empty tag.
     */
    public Tag() {

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
