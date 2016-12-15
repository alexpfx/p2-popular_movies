package udacity.nanodegree.android.p2;

import android.content.ContentValues;
import android.database.Cursor;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.detail.DetailFragment;
import udacity.nanodegree.android.p2.model.detail.DetailHandler;
import udacity.nanodegree.android.p2.model.detail.TrailerHandler;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;

import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener, DetailHandler.DetailHandlerDelegate, TrailerHandler.TrailerHandlerDelegate {

    private static final String TAG = "MainActivity";

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, name)
                .addToBackStack(null)
                .commit();
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

    @Override
    public void onFavorite(boolean isFavorited, MovieViewModel viewModel) {
        Log.d(TAG, "onFavorite: "+viewModel);
        if (isFavorited) {
            insertOrUpdate(viewModel);
        } else {
            deleteIfExists(viewModel);
        }
    }

    private void deleteIfExists(MovieViewModel viewModel) {
        if (movieExists(viewModel)) {
            deleteMovie(viewModel);
        }
    }

    private void deleteMovie(MovieViewModel viewModel) {
        getContentResolver().delete(MovieEntry.CONTENT_URI, MovieEntry.COLUMN_MOVIE_ID + "= ?", new String[]{String.valueOf(viewModel.getId())});
    }

    private void insertOrUpdate(MovieViewModel viewModel) {
        if (!movieExists(viewModel)) {
            insertMovie(viewModel);
        } else {
            updateMovie(viewModel);
        }
    }

    private void updateMovie(MovieViewModel viewModel) {

    }

    private boolean movieExists(MovieViewModel viewModel) {
        Cursor cursor = getContentResolver().query(MovieEntry.CONTENT_URI, new String[]{MovieEntry.COLUMN_MOVIE_ID}, MovieEntry.COLUMN_MOVIE_ID + "= ?", new String[]{String.valueOf(viewModel.getId())}, null);
        return cursor.moveToFirst();
    }

    private void insertMovie(MovieViewModel viewModel) {
        getContentResolver().insert(MovieEntry.CONTENT_URI, createContentValues(viewModel));
    }

    private ContentValues createContentValues(MovieViewModel viewModel) {
        ContentValues c = new ContentValues();
        c.put(MovieEntry.COLUMN_MOVIE_ID, viewModel.getId());
        c.put(MovieEntry.COLUMN_POSTER, viewModel.getPosterImage());
        c.put(MovieEntry.COLUMN_RELEASE, viewModel.getReleaseDate()
                .getTime());
        c.put(MovieEntry.COLUMN_SYNOPSIS, viewModel.getSynopsys());
        c.put(MovieEntry.COLUMN_TITLE, viewModel.getTitle());
        c.put(MovieEntry.COLUMN_USER_RATING, viewModel.getVoteAvg());
        c.put(MovieEntry.COLUMN_IS_FAVORITE, viewModel.isFavorite());
        //UPLOADED DATE
        //runtime
        return c;
    }

    @Override
    public void onTrailerPlay(String key) {
        Log.d(TAG, "onTrailerPlay: " + key);
        //        String key = vm.getKey();
//        Context context = view.getContext();
//        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.youtube_app, key)));
//
//        if (appIntent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(appIntent);
//        } else {
//            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.youtube_web, key)));
//            context.startActivity(webIntent);
//        }

    }
}
