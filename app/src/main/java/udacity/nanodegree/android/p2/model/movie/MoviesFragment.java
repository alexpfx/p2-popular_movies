package udacity.nanodegree.android.p2.model.movie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import udacity.nanodegree.android.p2.databinding.FragmentMoviesBinding;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.network.FetchMovies;
import udacity.nanodegree.android.p2.network.FetchRules;
import udacity.nanodegree.android.p2.network.data_transfer.Page;
import udacity.nanodegree.android.p2.network.data_transfer.Result;

public class MoviesFragment extends Fragment implements FetchMovies.Listener {
    private static final String TAG = "MoviesFragment";

    private OnMovieSelectedListener onMovieSelectedListener;
    private FragmentMoviesBinding binding;

    private RecyclerView recyclerView;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onMovieSelectedListener = (OnMovieSelectedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMovieSelectedListener = OnMovieSelectedListener.EMPTY;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoviesBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        initRecyclerView();
        fetchMovies(new GetPopularMovies());
        getActivity().setTitle(getString(R.string.action_most_popular));
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
        switch (id) {
            case R.id.action_order_popular:
                fetchMovies(new GetPopularMovies());
                break;
            case R.id.action_order_top:
                fetchMovies(new GetTopMovies());
                break;
        }
        getActivity().setTitle(item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movies_fragment_menu, menu);
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        Page page = gson.fromJson(response.toString(), Page.class);
        List<Result> results = page.getResults();
        List<MovieViewModel> paths = new ArrayList<>();
        for (Result r : results) {
            MovieViewModel viewModel = MovieViewModel.fromResult(r);
            viewModel.setOnMovieSelectedListener(onMovieSelectedListener);
            paths.add(viewModel);
        }
        binding.rvMovies.setAdapter(new MoviesAdapter(paths));
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
