package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IngredientTest {

    @Test
    public void SetNameTest() {
        Ingredient smoothie = new Ingredient("smoothie");
        smoothie.setName("Kale Smoothie");
        Assertions.assertEquals(smoothie.getName(), "Kale Smoothie");

    }
}
