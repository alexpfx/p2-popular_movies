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

import udacity.nanodegree.android.p2.databinding.FragmentDetailBinding;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;
import udacity.nanodegree.android.p2.model.comum.ViewModelCollection;
import udacity.nanodegree.android.p2.model.detail.review.GetReviews;
import udacity.nanodegree.android.p2.model.detail.review.ReviewListAdapter;
import udacity.nanodegree.android.p2.model.detail.review.ReviewModelConverter;
import udacity.nanodegree.android.p2.model.detail.review.ReviewViewModel;
import udacity.nanodegree.android.p2.model.detail.trailer.GetVideos;
import udacity.nanodegree.android.p2.model.detail.trailer.TrailerListAdapter;
import udacity.nanodegree.android.p2.model.detail.trailer.TrailerViewModelCollection;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;
import udacity.nanodegree.android.p2.network.FetchMovies;
import udacity.nanodegree.android.p2.network.data_transfer.Result;
import udacity.nanodegree.android.p2.network.data_transfer.Review;
import udacity.nanodegree.android.p2.network.data_transfer.ReviewItem;
import udacity.nanodegree.android.p2.network.data_transfer.Trailer;

import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry;

/**
 * Created by alexandre on 15/11/2016.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "DetailFragment";
    private static final int MOVIE_LOADER = 0;
    FragmentDetailBinding binding;
    private final FetchMovies.Listener reviewsListener = new FetchMovies.Listener() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            Review review = gson.fromJson(response.toString(), Review.class);
            ViewModelCollection<ReviewViewModel, ReviewItem> collection = new ViewModelCollection<ReviewViewModel, ReviewItem>(review.getResults(), new ReviewModelConverter());
            binding.rvReviews.setAdapter(new ReviewListAdapter(getContext(), collection));
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
                                    .query(MovieEntry.CONTENT_URI, MovieEntry.PROJECTION, MovieEntry.COLUMN_MOVIE_ID + " = ?", new String[]{id}, null);

        if (cursor.moveToFirst()) {
            binding.setVm(MovieViewModel.fromCursor(cursor));
        }

        new FetchMovies(new GetVideos(id), getContext(), videosListener).execute();
        new FetchMovies(new GetMovie(id), getContext(), movieDetailListener).execute();
        new FetchMovies(new GetReviews(id), getContext(), reviewsListener).execute();
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
        initRecyclerViews();
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

    private void initRecyclerViews() {
        RecyclerView.LayoutManager rvTrailersLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rvTrailers = binding.rvTrailers;
        rvTrailers.addItemDecoration(new DividerItemDecoration(getContext(), ((LinearLayoutManager) rvTrailersLayoutManager).getOrientation()));
        rvTrailers.setLayoutManager(rvTrailersLayoutManager);

        RecyclerView.LayoutManager rvReviewsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView rvReviews = binding.rvReviews;
        rvReviews.setLayoutManager(rvReviewsLayoutManager);
        rvReviews.addItemDecoration(new DividerItemDecoration(getContext(), ((LinearLayoutManager) rvReviewsLayoutManager).getOrientation()));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MovieEntry.CONTENT_URI, new String[]{MovieEntry.COLUMN_MOVIE_ID, MovieEntry.COLUMN_TITLE}, MovieEntry.COLUMN_MOVIE_ID + " = ?", new String[]{getMovieId()}, null);
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
