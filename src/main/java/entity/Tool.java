package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tools")
@SuppressWarnings("unused")
public class Tool implements Serializable, PreparationItem {
    /**
     * The name of the tool.
     */
    @DatabaseField(id = true, canBeNull = false)
    private String name;

    /**
     * Instantiates a new tool.
     *
     * @param name the name of the tool
     */
    public Tool(String name) {
        this.name = name;
    }

    /**
     * Instantiates an empty tool.
     */
    public Tool() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tool) {
            return name.equals(((Tool) o).getName());
        }
        return false;
    }
}
