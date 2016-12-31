package udacity.nanodegree.android.p2;

import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.detail.DetailFragment;
import udacity.nanodegree.android.p2.model.detail.DetailHandler;
import udacity.nanodegree.android.p2.model.detail.trailer.TrailerHandler;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;
import udacity.nanodegree.android.p2.model.movie.OnMovieSelectedListener;

public class MainActivity extends AppCompatActivity
        implements OnMovieSelectedListener, DetailHandler.DetailHandlerDelegate,
        TrailerHandler.TrailerHandlerDelegate {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(Global.PREFS_NAME, 0);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new MoviesFragment())
                .commit();
    }

    @Override
    public void onMovieSelected(MovieViewModel item) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,
                        DetailFragment.newInstance(String.valueOf(item.getId())))
                .addToBackStack("detail")
                .commit();
    }

    @Override
    public void onFavorite(boolean isFavorited, MovieViewModel viewModel) {
        if (isFavorited) {
            insertOrUpdate(viewModel);
        }
        updateFavorite(isFavorited, viewModel.getId());
    }

    private void insertOrUpdate(MovieViewModel viewModel) {
        if (!movieExists(viewModel)) {
            insertMovie(viewModel);
        } else {
            updateMovie(viewModel);
        }
    }

    private void updateFavorite(boolean isFavorited, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_IS_FAVORITE, isFavorited ? 1 : 0);
        getContentResolver().update(MovieEntry.CONTENT_URI, cv, MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(id)});
    }

    private void updateMovie(MovieViewModel viewModel) {
        getContentResolver().update(MovieEntry.CONTENT_URI, viewModel.createContentValues(),
                MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(viewModel.getId())});
    }

    private boolean movieExists(MovieViewModel viewModel) {
        Cursor cursor = getContentResolver().query(MovieEntry.CONTENT_URI,
                new String[]{MovieEntry.COLUMN_MOVIE_ID}, MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(viewModel.getId())}, null);
        return cursor.moveToFirst();
    }

    private void insertMovie(MovieViewModel viewModel) {
        getContentResolver().insert(MovieEntry.CONTENT_URI, viewModel.createContentValues());
    }

    @Override
    public void onTrailerPlay(String key) {

        Intent appIntent =
                new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_app, key)));

        if (appIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(appIntent);
        } else {
            Intent webIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_web, key)));
            startActivity(webIntent);
        }
    }
}
