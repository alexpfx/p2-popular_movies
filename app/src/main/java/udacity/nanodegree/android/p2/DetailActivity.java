package udacity.nanodegree.android.p2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import udacity.nanodegree.android.p2.database.MoviesContract;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.detail.DetailFragment;
import udacity.nanodegree.android.p2.model.detail.DetailHandler;
import udacity.nanodegree.android.p2.model.detail.trailer.TrailerHandler;

public class DetailActivity extends AppCompatActivity implements
        DetailHandler.DetailHandlerDelegate, TrailerHandler.TrailerHandlerDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            String movie_id = getIntent().getStringExtra(Global.KEY_MOVIE_ID);
            DetailFragment fragment = (DetailFragment) DetailFragment.newInstance(movie_id);
            getSupportFragmentManager().beginTransaction().add(R.id.main_container,
                    fragment).commit();

        }
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
        cv.put(MoviesContract.MovieEntry.COLUMN_IS_FAVORITE, isFavorited ? 1 : 0);
        getContentResolver().update(MoviesContract.MovieEntry.CONTENT_URI, cv,
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(id)});
    }

    private void updateMovie(MovieViewModel viewModel) {
        getContentResolver().update(MoviesContract.MovieEntry.CONTENT_URI,
                viewModel.createContentValues(),
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(viewModel.getId())});
    }

    private boolean movieExists(MovieViewModel viewModel) {
        Cursor cursor = getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI,
                new String[]{MoviesContract.MovieEntry.COLUMN_MOVIE_ID},
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + "= ?",
                new String[]{String.valueOf(viewModel.getId())}, null);
        return cursor.moveToFirst();
    }

    private void insertMovie(MovieViewModel viewModel) {
        getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI,
                viewModel.createContentValues());
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
