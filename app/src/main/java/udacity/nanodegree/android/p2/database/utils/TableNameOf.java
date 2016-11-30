package udacity.nanodegree.android.p2.database.utils;

import javax.persistence.Table;

/**
 * Created by alexandre on 26/11/2016.
 */

public class TableNameOf {
    private Class aClass;

    public TableNameOf(Class aClass) {
        this.aClass = aClass;
    }

    public String get() {
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

    @Override
    public String toString() {
        return get();
    }
}
