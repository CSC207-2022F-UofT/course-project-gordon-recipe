package entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tools")
public class Tool {
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
}
