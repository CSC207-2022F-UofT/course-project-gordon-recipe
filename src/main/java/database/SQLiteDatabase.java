package database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class SQLiteDatabase extends Database {
    /**
     * The ORMLite connection source for creating DAOs.
     */
    private final ConnectionSource connectionSource;

    /**
     * Initializes a new database from a URL to a persisted or in memory database.
     *
     * @param url the URL pointing to the database
     */
    public SQLiteDatabase(String url) throws SQLException {
        this.connectionSource = new JdbcConnectionSource(url);
    }

    @Override
    public <D extends Dao<Entity, ?>, Entity> D createDao(Class<Entity> entity) throws PersistenceError {
        try {
            TableUtils.createTableIfNotExists(connectionSource, entity);
            return DaoManager.createDao(connectionSource, entity);
        } catch (SQLException e) {
            throw new PersistenceError(e.getMessage());
        }
    }
}
