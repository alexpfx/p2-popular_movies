package udacity.nanodegree.android.p2.database;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by alexandre on 03/12/2016.
 */
public class SqliteDMLBuilderTest {
    @Test
    public void table() throws Exception {
        SqliteDMLBuilder my_table = SqliteDMLBuilder.createInstance()
                .table("my_table");

        Assert.assertEquals("create table my_table(", my_table.toString());

    }

    @Test
    public void column() throws Exception {
        SqliteDMLBuilder my_table = SqliteDMLBuilder.createInstance()
                .table("my_table")
                .column("my_column", "integer not null");

        Assert.assertEquals("create table my_table(my_column integer not null, ", my_table.toString());
    }

    @Test
    public void finish() throws Exception {
        String dml = SqliteDMLBuilder.createInstance()
                .table("my_table")
                .column("my_column", "integer not null")
                .column("col2", "text")
                .build();

        Assert.assertEquals("create table my_table(my_column integer not null, col2 text);", dml);
    }

}