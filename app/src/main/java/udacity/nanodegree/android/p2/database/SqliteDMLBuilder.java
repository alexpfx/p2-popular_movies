package udacity.nanodegree.android.p2.database;

/**
 * Created by alexandre on 03/12/2016.
 */

public class SqliteDMLBuilder {

    private StringBuilder stringBuilder = new StringBuilder();

    public static SqliteDMLBuilder createInstance() {
        return new SqliteDMLBuilder();
    }

    public SqliteDMLBuilder table(String name) {
        stringBuilder.append("create table ")
                     .append(name)
                     .append("(");
        return this;
    }

    public SqliteDMLBuilder column(String name, String specs) {
        stringBuilder.append(name)
                     .append(" ")
                     .append(specs)
                     .append(", ");
        return this;
    }

    public String build() {
        int indexOf = stringBuilder.lastIndexOf(",");
        if (indexOf >= 0) {
            stringBuilder.delete(indexOf, stringBuilder.length());
        }
        return stringBuilder.append(");")
                            .toString();
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

}
