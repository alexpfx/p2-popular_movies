package udacity.nanodegree.android.p2.database.utils;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import udacity.nanodegree.android.p2.util.SpacedStringBuilder;

/**
 * Created by alexandre on 22/11/2016.
 */

public class SqliteDmlBuilder {

    private Class aClass;

    public SqliteDmlBuilder(Class aClass) {
        this.aClass = aClass;
    }

    public String createSql() {
        SpacedStringBuilder sb = new SpacedStringBuilder("create table");
        sb.append(getTableName(aClass)).append("(");

        Field[] fields = aClass.getDeclaredFields();
        int fieldCounter = 0;

        for (Field f : fields) {

            if (!validateField(f)) {
                continue;
            }
            if (isColumn(f)) {
                sb.append(getColumnName(f)).append(getColumnType(f)).append(constraints(f));
                if (isId(f)) {
                    sb.append("primary key");
                }
                sb.append(",");
            }
        }
        int indexOf = sb.lastIndexOf(",");
        if (indexOf != -1) {
            sb.replace(indexOf, indexOf + 1, ");");
        }

        return sb.toString();
    }

    private boolean validateField(Field f) {
        if (!f.isAnnotationPresent(Column.class)) {
            return false;
        }
        if (f.getType().isPrimitive()) {
            throw new RuntimeException("Please, don't annotate primitives");
        }
        return true;
    }

    private String getColumnType(Field field) {
        String type = mapToSqliteType(field.getType());
        return type;
    }

    private String constraints(Field field) {
        SpacedStringBuilder sb = new SpacedStringBuilder();
        if (isUnique(field)) {
            sb.append("unique");
        }

        if (!isNullable(field)) {
            sb.append("not null");
        }
        return sb.toString();
    }

    private String mapToSqliteType(Class type) {
        if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(Float.class)) {
            return "REAL";
        }
        if (type.isAssignableFrom(Integer.class)) {
            return "INTEGER";
        }
        if (type.isAssignableFrom(String.class) || type.isAssignableFrom(Date.class)) {
            return "TEXT";
        }
        throw new RuntimeException("type not supported");
    }

    private String getColumnName(Field field) {
        Column column = (Column) field.getAnnotation(Column.class);
        String name = column.name();
        if (!name.equals("")) {
            return name;
        }
        return field.getName();
    }

    private boolean isUnique(Field field) {
        return field.getAnnotation(Column.class).unique();
    }

    private boolean isNullable(Field f) {
        return f.getAnnotation(Column.class).nullable();

    }

    private String getTableName(Class aClass) {
        return new TableNameOf(aClass).get();
    }

    private boolean isId(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isColumn(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    @Override
    public String toString() {
        return createSql();
    }
}
