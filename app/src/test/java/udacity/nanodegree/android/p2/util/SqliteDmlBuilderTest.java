package udacity.nanodegree.android.p2.util;

import org.junit.Test;

import udacity.nanodegree.android.p2.database.MovieDataModel;
import udacity.nanodegree.android.p2.database.utils.SqliteDmlBuilder;

/**
 * Created by alexandre on 23/11/2016.
 */
public class SqliteDmlBuilderTest {

    @Test
    public void createSql_1() throws Exception {
        SqliteDmlBuilder s = new SqliteDmlBuilder(MovieDataModel.class);
        String sql = s.createSql();
        System.out.println(sql);
//        Assert.assertEquals("create table Movies ( id INTEGER primary key, title TEXT, poster TEXT, synopsys TEXT, userRating REAL, releaseDate TEXT);", sql.toLowerCase());

    }

    @Test
    public void createSql_2() throws Exception {
        SqliteDmlBuilder s = new SqliteDmlBuilder(TestClass.class);
        String sql = s.createSql();
        System.out.println(sql);
//        Assert.assertEquals("create table table_test (string_column text primary key, integer_column integer unique nullable, double_column real, date_column text) ", sql.toLowerCase());

    }


}