package udacity.nanodegree.android.p2;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import udacity.nanodegree.android.p2.detail.DetailFragment;
import udacity.nanodegree.android.p2.home.MovieGridAdapter;
import udacity.nanodegree.android.p2.home.MoviesFragment;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new MoviesFragment()).commit();
        }
    }


    @Override
    public void onMovieSelected(MovieGridAdapter.Item item) {
        Log.d(TAG, "onMovieSelected: " + item);

        DetailFragment fragment = new DetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Bundle args = new Bundle();
        args.putString("movie_id", String.valueOf(item.getId()));
        fragment.setArguments(args);
    }
}
