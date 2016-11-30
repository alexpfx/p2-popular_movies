package udacity.nanodegree.android.p2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import udacity.nanodegree.android.p2.database.MoviesDbOpenHelper;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.detail.DetailFragment;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener {

    private static final String TAG = "MainActivity";
    private MoviesDbOpenHelper moviesDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*if fragment_movies doesn't exists, place the MoviesFragment into main_fragment_container.
         * if exists, so it is a tablet and the MoviesFragment is already in their own fragment view.
          */
        if (findViewById(R.id.fragment_movies) == null) {
            replaceMainContainer(new MoviesFragment(), "movies");
        }
    }

    private void replaceMainContainer(Fragment fragment, String name) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment, name).addToBackStack(null).commit();
    }

    @Override
    public void onMovieSelected(MovieViewModel item) {
        Log.d(TAG, "onMovieSelected: " + item);

        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("movie_id", String.valueOf(item.getId()));

        fragment.setArguments(args);
        replaceMainContainer(fragment, "detail");

    }
}
