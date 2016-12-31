package udacity.nanodegree.android.p2.model.movie;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import udacity.nanodegree.android.p2.R;
import udacity.nanodegree.android.p2.database.MoviesContract;
import udacity.nanodegree.android.p2.databinding.FragmentMoviesBinding;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.network.data_transfer.Page;
import udacity.nanodegree.android.p2.network.data_transfer.Result;
import udacity.nanodegree.android.p2.network.fetch.FetchMovies;
import udacity.nanodegree.android.p2.network.fetch.FetchRules;

public class MoviesFragment extends Fragment implements FetchMovies.Listener, LoaderManager
        .LoaderCallbacks<Cursor> {
    public static final int SPAN_COUNT = 2;
    private static final String TAG = "MoviesFragment";
    private static final int LOAD_FAVORITE_MOVIES = 100;

    private OnMovieSelectedListener onMovieSelectedListener;
    private FragmentMoviesBinding binding;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        binding = FragmentMoviesBinding.inflate(getLayoutInflater(savedInstanceState));
        initRecyclerView();
        if (savedInstanceState == null){
            MenuItem m = (MenuItem) getActivity().findViewById(R.id.action_popular_movies);
            onOptionsItemSelected(m);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onMovieSelectedListener = (OnMovieSelectedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        onMovieSelectedListener = OnMovieSelectedListener.EMPTY;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {



        Log.d(TAG, "onCreateView: "+savedInstanceState);
        return binding.getRoot();

    }

    private void fetchMovies(FetchRules fetchRules) {
        new FetchMovies(fetchRules, this.getContext(), this).run();
    }

    private void initRecyclerView() {
        binding.rvMovies.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(!item.isChecked());

        switch (id) {
            case R.id.action_popular_movies:
                fetchMovies(new GetPopularMovies());
                break;
            case R.id.action_top_rated:
                fetchMovies(new GetTopMovies());
                break;
            case R.id.action_favorite_movies:
                getLoaderManager().initLoader(LOAD_FAVORITE_MOVIES, null, this);
                Log.d(TAG, "onOptionsItemSelected: init loader");
                break;

        }
        getActivity().setTitle(item.getTitle());

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movies_fragment_menu, menu);
    }

    private void restoreRvState(Parcelable state) {
        binding.rvMovies.getLayoutManager()
                .onRestoreInstanceState(state);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MoviesContract.MovieEntry.CONTENT_URI,
                MoviesContract.MovieEntry.PROJECTION, MoviesContract.MovieEntry
                .COLUMN_IS_FAVORITE + "= 1", null, MoviesContract.MovieEntry.COLUMN_TITLE + " asc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: ");
        if (data == null || !data.moveToFirst()) {
            return;
        }
        List<MovieViewModel> movies = new ArrayList<>();
        do {
            MovieViewModel model = MovieViewModel.fromCursor(data);
            model.setOnMovieSelectedListener(onMovieSelectedListener);
            movies.add(model);
        } while (data.moveToNext());

        if (binding.rvMovies.getAdapter() != null) {
            MoviesAdapter adapter = (MoviesAdapter) binding.rvMovies.getAdapter();
            adapter.setMovies(movies);
        } else {
            binding.rvMovies.setAdapter(new MoviesAdapter(movies));
        }

        getLoaderManager().destroyLoader(LOAD_FAVORITE_MOVIES);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "onResponse: ");
        Gson gson = new Gson();
        Page page = gson.fromJson(response.toString(), Page.class);
        List<Result> results = page.getResults();
        List<MovieViewModel> paths = new ArrayList<>();
        for (Result r : results) {
            MovieViewModel viewModel = MovieViewModel.fromResult(r);
            viewModel.setOnMovieSelectedListener(onMovieSelectedListener);
            paths.add(viewModel);
        }
        if (binding.rvMovies.getAdapter() != null) {
            MoviesAdapter adapter = (MoviesAdapter) binding.rvMovies.getAdapter();
            adapter.setMovies(paths);
        } else {
            binding.rvMovies.setAdapter(new MoviesAdapter(paths));
        }

    }

    @Override
    public void onError(int networkStatusCode, Throwable cause) {
        Log.e(TAG, "onError: " + String.valueOf(networkStatusCode), cause);
    }

}
