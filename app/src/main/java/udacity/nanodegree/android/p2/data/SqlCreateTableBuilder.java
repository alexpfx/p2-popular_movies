package udacity.nanodegree.android.p2.data;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alexandre on 22/11/2016.
 */

public class SqlCreateTableBuilder {

    private Class aClass;

    public SqlCreateTableBuilder(Class aClass) {
        this.aClass = aClass;
    }


    public String createSql() {
        SpacedStringBuilder sb = new SpacedStringBuilder("create table");
        sb.append(getTableName(aClass)).append("(");

        Field[] fields = aClass.getDeclaredFields();
        int fn = 0;
        for (Field f : fields) {

            validateField(f);
            if (isColumn(f)) {
                sb.append(getColumnName(f)).append(getColumnType(f)).append(constraints(f));
                if (isId(f)) {
                    sb.append("primary key");
                }
            }

            if (++fn != fields.length) {
                sb.append(",");
            } else {
                sb.append(");");
            }
        }
        return sb.toString();
    }

    private void validateField(Field f) {
        if (!f.isAnnotationPresent(Column.class)) {
            return;
        }
        if (f.getType().isPrimitive()) {
            throw new RuntimeException("Please, don't annotate primitives");
        }
    }


    private String getColumnType(Field field) {
        String type = mapToSqliteType(field.getType());
        return type;
    }

    private String constraints(Field field) {
        StringBuilder sb = new StringBuilder();
        if (!isNullable(field)) {
            sb.append(" not null ");
        }
        if (isUnique(field)) {
            sb.append(" unique ");
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
        if (isTable(aClass)) {
            Table t = (Table) aClass.getAnnotation(Table.class);
            String name = t.name();
            if (!name.equals("")) {
                return name;
            }
        }
        return aClass.getSimpleName();
    }

    private boolean isTable(Class aClass) {
        return aClass.isAnnotationPresent(Table.class);
    }


    private boolean isId(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isColumn(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

}
