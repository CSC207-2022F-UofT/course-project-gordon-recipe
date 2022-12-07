package entity;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Ingredient implements Serializable, PreparationItem {
    /**
     * The name of the ingredient.
     */
    @DatabaseField(canBeNull = false, id = true)
    private String name;

    /**
     * Instantiates a new ingredient.
     *
     * @param name The name of the ingredient
     */
    public Ingredient(String name) {
        this.name = name;
    }

    /**
     * Instantiates an empty ingredient.
     */
    public Ingredient() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ingredient) {
            return name.equals(((Ingredient) o).getName());
        }
        return false;
    }
}
