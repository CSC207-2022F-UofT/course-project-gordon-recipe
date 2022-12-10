package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TagTest {
    @Test
    public void SetNameTest() {
        Tag tag = new Tag("vegan");

        tag.setName("vegetarian");

        Assertions.assertEquals(tag.getName(), "vegetarian");
    }

    @Test
    public void CreateRecipeTagTest() {
        Tag vegan = new Tag("vegan");
        Recipe pie = new Recipe("Pie", 10, 10);

        RecipeTag recipeTag = new RecipeTag(pie, vegan, 10);

        Assertions.assertEquals(10, recipeTag.getID());
        Assertions.assertEquals(pie, recipeTag.getRecipe());
        Assertions.assertEquals(vegan, recipeTag.getPreparationItem());
    }
}
