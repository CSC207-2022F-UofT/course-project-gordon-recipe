package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tags")
public class Tag {
    /**
     * The name of the tag.
     */
    @DatabaseField(id = true, canBeNull = false)
    private String name;

    /**
     * Instantiates a new tag.
     *
     * @param name the name of the tag
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Instantiates an empty tag.
     */
    public Tag() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
