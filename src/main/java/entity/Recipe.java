package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import database.LocalDateTimePersister;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@DatabaseTable(tableName = "recipes")
public class Recipe implements Serializable {
    /**
     * The ID of the recipe.
     */
    @DatabaseField(id = true, canBeNull = false)
    private String id;

    /**
     * The name of the recipe.
     */
    @DatabaseField(canBeNull = false, width = 150)
    private String name;

    /**
     * Number of people the recipe will serve.
     */
    @DatabaseField(canBeNull = false)
    private int servings;

    /**
     * Number of times the user has prepared the recipe.
     */
    @DatabaseField(canBeNull = false)
    private int timesCooked;

    /**
     * Number of minutes needed to prepare the recipe.
     */
    @DatabaseField(canBeNull = false)
    private int prepTime;

    /**
     * Whether the user likes the recipe or not.
     */
    @DatabaseField(canBeNull = false)
    private boolean thumbsUp;

    /**
     * The date and time the recipe was created.
     */
    @DatabaseField(canBeNull = false, persisterClass = LocalDateTimePersister.class)
    private LocalDateTime date;

    /**
     * Instantiates a new recipe that the user has not yet cooked.
     * Sets timesCooked to 0, thumbsUp to false, date to now, and generates a random ID.
     *
     * @param name     The recipe's name
     * @param servings Number of servings
     * @param prepTime Prep time of the recipe in minutes
     */
    public Recipe(String name, int servings, int prepTime) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.servings = servings;
        this.prepTime = prepTime;
        this.thumbsUp = false;
        this.timesCooked = 0;
        this.date = LocalDateTime.now();
    }

    /**
     * Instantiates an empty recipe.
     */
    public Recipe() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getTimesCooked() {
        return timesCooked;
    }

    public void setTimesCooked(int timesCooked) {
        this.timesCooked = timesCooked;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public boolean getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
