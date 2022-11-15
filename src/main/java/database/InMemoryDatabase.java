package database;

import java.sql.SQLException;

public class InMemoryDatabase extends Database {
    public InMemoryDatabase() throws SQLException {
        super("jdbc:sqlite::memory:");
    }
}
