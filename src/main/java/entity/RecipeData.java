package entity;

import java.io.Serializable;
import java.util.List;

/**
 * All the data (ingredients, tags, tools, steps, notes, etc) for a recipe,
 * organized into one object that can be written to and read from a file.
 */
public class RecipeData implements Serializable {
    private final Recipe recipe;
    private final List<RecipeIngredient> recipeIngredients;
    private final List<RecipeTag> recipeTags;
    private final List<RecipeTool> recipeTools;
    private final List<Step> steps;
    private final List<Note> notes;

    public RecipeData(
            Recipe recipe,
            List<RecipeIngredient> recipeIngredients,
            List<RecipeTag> recipeTags,
            List<RecipeTool> recipeTools,
            List<Step> steps,
            List<Note> notes
    ){
        this.recipe = recipe;
        this.recipeIngredients = recipeIngredients;
        this.recipeTags = recipeTags;
        this.recipeTools = recipeTools;
        this.steps = steps;
        this.notes = notes;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public List<RecipeTag> getRecipeTags() {
        return recipeTags;
    }

    public List<RecipeTool> getRecipeTools() {
        return recipeTools;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
