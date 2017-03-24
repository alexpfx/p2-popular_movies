package udacity.nanodegree.android.p2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;
import udacity.nanodegree.android.p2.model.movie.OnMovieSelectedListener;
import udacity.nanodegree.android.p2.util.ContextUtil;

public class MainActivity extends AppCompatActivity implements OnMovieSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: externalizar.
        ContextUtil.setupToolbar(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new MoviesFragment(), "movies")
                    .commit();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", getTitle().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setTitle(savedInstanceState.getString("title"));
    }

    @Override
    public void onMovieSelected(MovieViewModel item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Global.KEY_MOVIE_ID, String.valueOf(item.getId()));

        startActivity(intent);
    }


}
