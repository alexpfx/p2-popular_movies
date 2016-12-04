package udacity.nanodegree.android.p2.database;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by alexandre on 03/12/2016.
 */
public class DmlHelperTest {
    @Test
    public void table() throws Exception {
        DmlHelper my_table = DmlHelper.createInstance()
                .table("my_table");

        Assert.assertEquals("create table my_table(", my_table.toString());

    }

    @Test
    public void column() throws Exception {
        DmlHelper my_table = DmlHelper.createInstance()
                .table("my_table")
                .column("my_column", "integer not null");

        Assert.assertEquals("create table my_table(my_column integer not null, ", my_table.toString());
    }

    @Test
    public void finish() throws Exception {
        String dml = DmlHelper.createInstance()
                .table("my_table")
                .column("my_column", "integer not null")
                .column("col2", "text")
                .finish();

        Assert.assertEquals("create table my_table(my_column integer not null, col2 text);", dml);
    }

}