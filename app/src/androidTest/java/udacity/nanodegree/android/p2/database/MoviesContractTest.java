package udacity.nanodegree.android.p2.database;

import android.net.Uri;
import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alexandre on 06/12/2016.
 */
public class MoviesContractTest {
    private static final String TAG = "MoviesContractTest";

    @Test
    public void movieContractTest(){
        Uri uri = MoviesContract.MovieEntry.buildMovieUri(1L);
        Log.d(TAG, "movieContractTest: "+uri);
    }

}