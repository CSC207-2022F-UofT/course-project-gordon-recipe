package database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * An abstract class which handles storing DAOs (Database Access Objects).
 */
public abstract class Database {
    private final HashMap<Class<?>, Dao<?, ?>> daoHashMap = new HashMap<>();

    abstract public <D extends Dao<Entity, ?>, Entity> D createDao(Class<Entity> entity) throws SQLException;

    public <Entity, ID> Dao<Entity, ID> getDao(Class<Entity> entity) {
        @SuppressWarnings("unchecked")
        Dao<Entity, ID> dao = (Dao<Entity, ID>) daoHashMap.get(entity);

        if (dao == null) {
            try {
                dao = createDao(entity);
                daoHashMap.put(entity, dao);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return dao;
    }
}
