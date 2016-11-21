package udacity.nanodegree.android.p2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import udacity.nanodegree.android.p2.detail.DetailFragment;
import udacity.nanodegree.android.p2.home.MovieGridAdapter;
import udacity.nanodegree.android.p2.home.MoviesFragment;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener {

    private static final String TAG = "MainActivity";
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*if fragment_movies doesn't exists, place the MoviesFragment into main_fragment_container.
         * if exists, so it is a tablet and the MoviesFragment is already in their own fragment view.
          */
        if (findViewById(R.id.fragment_movies) == null) {
            replaceMainContainer(new MoviesFragment(), "");
        }


    }

    private void replaceMainContainer(Fragment fragment, String name) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
    }


    @Override
    public void onMovieSelected(MovieGridAdapter.Item item) {
        Log.d(TAG, "onMovieSelected: " + item);

        DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detail);

        Bundle args = new Bundle();
        args.putString("movie_id", String.valueOf(item.getId()));
        fragment.updateMovie(String.valueOf(item.getId()));


    }
}
