package udacity.nanodegree.android.p2.model.movie;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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

import udacity.nanodegree.android.p2.Global;
import udacity.nanodegree.android.p2.R;
import udacity.nanodegree.android.p2.database.MoviesContract;
import udacity.nanodegree.android.p2.databinding.FragmentMoviesBinding;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.network.FetchMovies;
import udacity.nanodegree.android.p2.network.FetchRules;
import udacity.nanodegree.android.p2.network.data_transfer.Page;
import udacity.nanodegree.android.p2.network.data_transfer.Result;

public class MoviesFragment extends Fragment implements FetchMovies.Listener, LoaderManager
        .LoaderCallbacks<Cursor> {
    private static final String TAG = "MoviesFragment";
    private static final int LOAD_FAVORITE_MOVIES = 100;
    public static final String ACTION_ID = "actionId";

    private OnMovieSelectedListener onMovieSelectedListener;
    private FragmentMoviesBinding binding;


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreate: ");
        getLoaderManager().initLoader(LOAD_FAVORITE_MOVIES, null, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onMovieSelectedListener = (OnMovieSelectedListener) context;
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMovieSelectedListener = OnMovieSelectedListener.EMPTY;
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = FragmentMoviesBinding.inflate(getLayoutInflater(savedInstanceState), container,
                                                false);
        initRecyclerView();
        return binding.getRoot();
        
    }

    private void fetchMovies(FetchRules fetchRules) {
        new FetchMovies(fetchRules, this.getContext(), this).execute();
    }

    private void initRecyclerView() {
        GridLayoutManager layout = new GridLayoutManager(getContext(), 3);

        binding.rvMovies.setLayoutManager(layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(!item.isChecked());
        Log.d(TAG, "onOptionsItemSelected: "+item);
        switch (id) {
            case R.id.action_popular_movies:
                fetchMovies(new GetPopularMovies());
                break;
            case R.id.action_top_rated:
                fetchMovies(new GetTopMovies());
                break;
            case R.id.action_favorite_movies:

                getLoaderManager().initLoader(LOAD_FAVORITE_MOVIES,null, this);
                break;

        }
        getActivity().setTitle(item.getTitle());

        int actionId = getActionId();
        if (actionId != item.getItemId()){
            setLastActionId(item.getItemId());
        }

        return super.onOptionsItemSelected(item);
    }

    int position = 0;
    public void setLastActionId(int actionId){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Global.PREFS_NAME, 0);
        sharedPreferences.edit().putInt(ACTION_ID, actionId).putInt("LAST_ITEM_POSITION", position).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movies_fragment_menu, menu);
        Log.d(TAG, "onCreateOptionsMenu: ");

        int actionId = getActionId();
        onOptionsItemSelected(menu.findItem(actionId));
    }



    private int getActionId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Global.PREFS_NAME, 0);
        return sharedPreferences.getInt(ACTION_ID, R.id.action_favorite_movies);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MoviesContract.MovieEntry.CONTENT_URI, MoviesContract.MovieEntry.PROJECTION, MoviesContract.MovieEntry
                .COLUMN_IS_FAVORITE + "= 1", null, MoviesContract.MovieEntry.COLUMN_TITLE + " asc");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: "+data.getCount());
        if (data == null || !data.moveToFirst()) {
            return;
        }
        List<MovieViewModel> movies = new ArrayList<>();
        do {
            MovieViewModel model = MovieViewModel.fromCursor(data);
            model.setOnMovieSelectedListener(onMovieSelectedListener);
            movies.add(model);
        } while (data.moveToNext());
        if (binding.rvMovies.getAdapter() != null){
            MoviesAdapter adapter = (MoviesAdapter) binding.rvMovies.getAdapter();
            adapter.setMovies(movies);
        }else {
            binding.rvMovies.setAdapter(new MoviesAdapter(movies));
        }


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
        if (binding.rvMovies.getAdapter() != null){
            MoviesAdapter adapter = (MoviesAdapter) binding.rvMovies.getAdapter();
            adapter.setMovies(paths);
        }else {
            binding.rvMovies.setAdapter(new MoviesAdapter(paths));
        }

    }

    private void scroolToPositionAfter(final int position, long milliseconds) {
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.rvMovies.getLayoutManager().smoothScrollToPosition(binding.rvMovies, null, position);
            }
        }, milliseconds);
    }

    @Override
    public void onError(int networkStatusCode, Throwable cause) {
        Log.e(TAG, "onError: " + String.valueOf(networkStatusCode), cause);
    }

    public interface OnMovieSelectedListener {

        void onMovieSelected(MovieViewModel item);

        OnMovieSelectedListener EMPTY = new OnMovieSelectedListener() {
            @Override
            public void onMovieSelected(MovieViewModel item) {

            }
        };

    }
}
