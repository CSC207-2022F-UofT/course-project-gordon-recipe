package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tags")
@SuppressWarnings("unused")
public class Tag implements Serializable, PreparationItem {
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag) {
            return name.equals(((Tag) o).getName());
        }
        return false;
    }
}
