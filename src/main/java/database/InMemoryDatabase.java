package database;

import java.sql.SQLException;

/**
 * A database stored in memory, and not persisted to disk.
 * This database should be used for testing, so that tests do not affect saved recipes.
 */
public class InMemoryDatabase extends Database {
    public InMemoryDatabase() throws SQLException {
        super("jdbc:sqlite::memory:");
    }
}
