package database;

import com.j256.ormlite.dao.Dao;

import java.util.HashMap;

/**
 * An abstract class which handles storing DAOs (Database Access Objects).
 * NOTE:
 * The current setup for DAOs might seem to violate clean architecture, as the DAO interface is taken from Ormlite,
 * an SQL specific library. This choice was made out of the time constraint, as it seemed sensible to use an already existing
 * interface as opposed to making a new one and trying to fit Ormlite to it. However, because the interface is just that,
 * an interface, it is enough to implement a <code>Database</code> that is not SQL-like.
 */
public abstract class Database {
    /**
     * Because DAOs can be expensive to create, we cache them in a hash map.
     */
    private final HashMap<Class<?>, Dao<?, ?>> daoHashMap = new HashMap<>();

    /**
     * Creates and returns a DAO
     *
     * @param entity   the class of the entity that needs a new DAO
     * @param <D>      The DAO
     * @param <Entity> The type of the entity the DAO should be of
     * @return the DAO of the entity
     * @throws PersistenceError an error if creating the DAO failed
     */
    abstract public <D extends Dao<Entity, ?>, Entity> D createDao(Class<Entity> entity) throws PersistenceError;

    /**
     * Fetches a cached DAO or creates a new one if the DAO is not already cached.
     *
     * @param entity   the class of the entity that needs a new DAO
     * @param <Entity> The type of the entity the DAO should be of
     * @param <ID>     The type of the ID field of the entity
     * @return the DAO of the entity
     */
    public <Entity, ID> Dao<Entity, ID> getDao(Class<Entity> entity) {
        @SuppressWarnings("unchecked")
        Dao<Entity, ID> dao = (Dao<Entity, ID>) daoHashMap.get(entity);

        if (dao == null) {
            try {
                dao = createDao(entity);
                daoHashMap.put(entity, dao);
            } catch (PersistenceError e) {
                throw new RuntimeException(e);
            }
        }

        return dao;
    }

    /**
     * A generic exception for any database errors.
     */
    static public class PersistenceError extends Exception {
        public PersistenceError(String message) {
            super(message);
        }
    }
}
