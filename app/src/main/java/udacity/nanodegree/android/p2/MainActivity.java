package udacity.nanodegree.android.p2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;
import udacity.nanodegree.android.p2.model.movie.OnMovieSelectedListener;

public class MainActivity extends AppCompatActivity implements OnMovieSelectedListener {

    public static final String MOVIES_TAG = "movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MoviesFragment(), "movies")
                    .commitAllowingStateLoss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Fragment movies = getSupportFragmentManager().findFragmentByTag("movies");
        if (movies != null) {
            movies.setRetainInstance(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment movies = getSupportFragmentManager().findFragmentByTag(MOVIES_TAG);
        if (movies != null) {
            movies.getRetainInstance();
        }
    }

    @Override
    public void onMovieSelected(MovieViewModel item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Global.KEY_MOVIE_ID, String.valueOf(item.getId()));

        startActivity(intent);
    }


}
