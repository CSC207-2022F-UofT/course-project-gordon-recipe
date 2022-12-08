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


}
