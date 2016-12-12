package udacity.nanodegree.android.p2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import udacity.nanodegree.android.p2.database.MoviesContentProvider;
import udacity.nanodegree.android.p2.database.MoviesOpenHelper;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.detail.DetailFragment;
import udacity.nanodegree.android.p2.model.detail.DetailHandler;
import udacity.nanodegree.android.p2.model.detail.TrailerHandler;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener, DetailHandler.DetailHandlerDelegate, TrailerHandler.TrailerHandlerDelegate {

    private static final String TAG = "MainActivity";
    private MoviesContentProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        provider = new MoviesContentProvider();



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
        Log.d(TAG, "onFavorite: " + isFavorited);
        Log.d(TAG, "onFavorite: " + viewModel);
    }

    @Override
    public void onTrailerPlay(String key) {
        Log.d(TAG, "onTrailerPlay: "+key);
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
