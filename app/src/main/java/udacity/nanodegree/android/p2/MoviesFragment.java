package udacity.nanodegree.android.p2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import udacity.nanodegree.android.p2.domain.Page;
import udacity.nanodegree.android.p2.domain.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements VolleyFetch.Listener {
    private static final String TAG = "MoviesFragment";

    @BindView(R.id.grid_movies)
    GridView movieGrid;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @OnItemClick(R.id.grid_movies)
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ImageAdapter.Item item = (ImageAdapter.Item) adapterView.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, String.valueOf(item.getId()));
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        fetchMovies(new GetPopularMovies());
        getActivity().setTitle(getString(R.string.action_most_popular));

        return view;
    }

    private void fetchMovies(FetchRules fetchRules) {
        new VolleyFetch(fetchRules, this.getContext(), this).execute();
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
        List<ImageAdapter.Item> paths = new ArrayList<>();
        for (Result r : results) {
            paths.add(new ImageAdapter.Item(r.getId(), r.getPosterPath()));
        }
        movieGrid.setAdapter(new ImageAdapter(paths, getContext()));
    }

    @Override
    public void onError(int networkStatusCode, Throwable cause) {
        Log.e(TAG, "onError: " + String.valueOf(networkStatusCode), cause);
    }
}
