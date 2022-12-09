package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class NoteTest {
    @Test
    public void NoteGetIdTest() {
        Recipe coconutMilk = new Recipe("Coconut Milk", 1, 1);
        Note myNote = new Note("gives me strange visions", coconutMilk);
        myNote.setID("7");

        Assertions.assertEquals(myNote.getID(), "7");
    }

    @Test
    public void NoteGetRecipeTest() {
        Recipe coconutMilk = new Recipe("Coconut Milk", 1, 1);
        Note myNote = new Note("gives me strange visions", coconutMilk);

        Assertions.assertEquals(myNote.getRecipe(), coconutMilk);

    }

    @Test
    public void NoteSetRecipeTest() {
        Recipe coconutMilk = new Recipe("Coconut Milk", 1, 1);
        Recipe mushroomPizza = new Recipe("Mushroom Pizza", 1, 1);
        Note myNote = new Note("gives me strange visions", coconutMilk);

        myNote.setRecipe(mushroomPizza);

        Assertions.assertEquals(myNote.getRecipe(), mushroomPizza);

    }

    @Test
    public void NoteDateTest() {
        Recipe coconutMilk = new Recipe("Coconut Milk", 1, 1);
        Note myNote = new Note("gives me strange visions", coconutMilk);
        LocalDateTime time = LocalDateTime.now();

        myNote.setDate(time);

        Assertions.assertEquals(time, myNote.getDate());
    }


}
