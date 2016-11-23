package udacity.nanodegree.android.p2.data;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by alexandre on 23/11/2016.
 */
public class SqlCreateTableBuilderTest {
    @Test
    public void createSql() throws Exception {
        SqlCreateTableBuilder s = new SqlCreateTableBuilder(MovieData.class);
        String sql = s.createSql();
        Assert.assertEquals("create table Movies ( id INTEGER primary key , title TEXT , poster TEXT , synopsys TEXT , userRating REAL , releaseDate TEXT );", sql);
    }

}