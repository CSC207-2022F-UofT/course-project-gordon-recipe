package database;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.StringType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Helper to persist the java class LocalDateTime in the database.
 * We need this helper because ORMLite does not natively support storing LocalDateTime.
 */
public class LocalDateTimePersister extends StringType {
    private static final LocalDateTimePersister singleton = new LocalDateTimePersister();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    public LocalDateTimePersister() {
        super(SqlType.STRING, new Class<?>[]{LocalDateTime.class});
    }

    public static LocalDateTimePersister getSingleton() {
        return singleton;
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return LocalDateTime.from(formatter.parse((String) sqlArg));
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return formatter.format((LocalDateTime) javaObject);
    }
}
