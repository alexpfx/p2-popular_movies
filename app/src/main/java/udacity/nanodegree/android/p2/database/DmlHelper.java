package udacity.nanodegree.android.p2.database;

import udacity.nanodegree.android.p2.util.SpacedStringBuilder;

/**
 * Created by alexandre on 03/12/2016.
 */

public class DmlHelper {

    private StringBuilder stringBuilder = new StringBuilder();

    public DmlHelper table(String name) {
        stringBuilder.append("create table ").append(name)
                .append("(");
        return this;
    }

    public DmlHelper column(String name, String specs) {
        stringBuilder.append(name).append(" ")
                .append(specs)
                .append(", ");
        return this;
    }

    public String finish() {
        int indexOf = stringBuilder.lastIndexOf(",");
        if (indexOf >= 0) {
            stringBuilder.delete(indexOf, stringBuilder.length());
        }
        return stringBuilder.append(");")
                .toString();
    }

    public static DmlHelper createInstance() {
        return new DmlHelper();
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }


}
