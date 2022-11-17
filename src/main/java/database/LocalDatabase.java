package database;

import java.sql.SQLException;

/**
 * A persisted database stored on the user's local disk.
 * This database should be used for running use-cases, and should not be used for tests of use-cases.
 */
public class LocalDatabase extends SQLiteDatabase {
    public static final LocalDatabase singleton = getSingleton();

    public LocalDatabase() throws SQLException {
        super("jdbc:sqlite:recipes.db");
    }

    static private LocalDatabase getSingleton() {
        try {
            return new LocalDatabase();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            System.exit(1);
            return null;
        }
    }
}
