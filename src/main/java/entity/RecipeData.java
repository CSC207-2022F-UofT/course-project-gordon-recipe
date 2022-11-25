package entity;

import java.io.Serializable;
import java.util.List;

/**
 * All the data (ingredients, tags, tools, steps, notes, etc) for a recipe,
 * organized into one object that can be written to and read from a file.
 */
public record RecipeData(
        Recipe recipe,
        List<RecipeIngredient> recipeIngredients,
        List<RecipeTag> recipeTags,
        List<RecipeTool> recipeTools,
        List<Step> steps,
        List<Note> notes
) implements Serializable {
}
