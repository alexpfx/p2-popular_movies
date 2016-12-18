package udacity.nanodegree.android.p2.model.detail;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.google.gson.Gson;

import org.json.JSONObject;

import udacity.nanodegree.android.p2.database.MoviesContract;
import udacity.nanodegree.android.p2.databinding.FragmentDetailBinding;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.detail.trailer.GetVideos;
import udacity.nanodegree.android.p2.model.detail.trailer.TrailerListAdapter;
import udacity.nanodegree.android.p2.model.detail.trailer.TrailerViewModelCollection;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;
import udacity.nanodegree.android.p2.network.FetchMovies;
import udacity.nanodegree.android.p2.network.data_transfer.Result;
import udacity.nanodegree.android.p2.network.data_transfer.Trailer;

/**
 * Created by alexandre on 15/11/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "DetailFragment";
    private static final int MOVIE_LOADER = 0;
    FragmentDetailBinding binding;
    private MoviesFragment.OnMovieSelectedListener onMovieSelectedListener;
    private DetailHandler.DetailHandlerDelegate detailHandlerDelegate;
    private CursorAdapter cursorAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public void updateMovie(String id) {
        if (id == null) {
            return;
        }
        Cursor cursor = getContext().getContentResolver()
                .query(MoviesContract.MovieEntry.CONTENT_URI, null, MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", new String[]{id}, null);
        MovieViewModel vm = MovieViewModel.fromCursor(cursor);
        binding.setVm(vm);

        new FetchMovies(new GetVideos(id), getContext(), videosListener).execute();
        new FetchMovies(new GetMovie(id), getContext(), movieDetailListener).execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onMovieSelectedListener = (MoviesFragment.OnMovieSelectedListener) context;
        detailHandlerDelegate = (DetailHandler.DetailHandlerDelegate) context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater(savedInstanceState), container, false);
        binding.setHandler(new DetailHandler(detailHandlerDelegate));
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        onMovieSelectedListener = MoviesFragment.OnMovieSelectedListener.EMPTY;
    }

    private String getMovieId() {
        Bundle arguments = getArguments();
        String id = null;

        if (arguments != null) {
            id = arguments.getString("movie_id");
        }
        return id;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateMovie(getMovieId());
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rvTrailers = binding.rvTrailers;

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTrailers.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        rvTrailers.addItemDecoration(dividerItemDecoration);
        rvTrailers.setLayoutManager(layoutManager);
    }

    private final FetchMovies.Listener movieDetailListener = new FetchMovies.Listener() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            Result result = gson.fromJson(response.toString(), Result.class);

            MovieViewModel oldVm = binding.getVm();
            MovieViewModel vm = MovieViewModel.fromResult(result);
            if (oldVm != null) {
                vm.setFavorite(oldVm.isFavorite());
            }
            binding.setVm(vm);
        }

        @Override
        public void onError(int networkStatusCode, Throwable cause) {
        }
    };

    private final FetchMovies.Listener videosListener = new FetchMovies.Listener() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            Trailer trailer = gson.fromJson(response.toString(), Trailer.class);
            binding.rvTrailers.setAdapter(new TrailerListAdapter(getContext(), new TrailerViewModelCollection(trailer.getResults())));

        }

        @Override
        public void onError(int networkStatusCode, Throwable cause) {

        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MoviesContract.MovieEntry.CONTENT_URI, new String[]{MoviesContract.MovieEntry.COLUMN_MOVIE_ID, MoviesContract.MovieEntry.COLUMN_TITLE}, MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", new String[]{getMovieId()}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }
        do {
        } while (data.moveToNext());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
