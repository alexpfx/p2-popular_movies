package udacity.nanodegree.android.p2.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.nanodegree.android.p2.R;
import udacity.nanodegree.android.p2.VolleyFetch;
import udacity.nanodegree.android.p2.domain.Result;
import udacity.nanodegree.android.p2.domain.Trailer;

/**
 * Created by alexandre on 15/11/2016.
 */
public class DetailFragment extends Fragment {

    @BindView(R.id.text_title)
    TextView txtTitle;
    @BindView(R.id.image_poster)
    ImageView imgPoster;

    @BindView(R.id.text_vote_avg)
    TextView txtVoteAvg;

    @BindView(R.id.text_overview)
    TextView txtOverview;

    @BindView(R.id.text_release_year)
    TextView txtReleaseDate;

    @BindView(R.id.text_runtime)
    TextView txtRuntime;

    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        Bundle arguments = getArguments();
        String id = null;
        if (arguments != null) {
            id = arguments.getString("movie_id");
            new VolleyFetch(new GetVideos(id), getContext(), videosListener).execute();
            new VolleyFetch(new GetMovie(id), getContext(), movieDetailListener).execute();
        }
        return view;
    }

    private void initRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTrailers.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        rvTrailers.addItemDecoration(dividerItemDecoration);
        rvTrailers.setLayoutManager(layoutManager);
    }


    private final VolleyFetch.Listener movieDetailListener = new VolleyFetch.Listener() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            Result result = gson.fromJson(response.toString(), Result.class);


            txtTitle.setText(result.getOriginalTitle());
            String path = getString(R.string.tmdb_image_base_path) + result.getPosterPath();

            Picasso.with(getContext()).load(path).placeholder(R.drawable.ic_autorenew_black_48dp).error(R.drawable.ic_error_black_48dp).into(imgPoster);
            Calendar calendar;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.US);
                calendar = Calendar.getInstance();
                calendar.setTime(simpleDateFormat.parse(result.getReleaseDate()));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            txtReleaseDate.setText(String.valueOf(calendar.get(Calendar.YEAR)));

            txtVoteAvg.setText(getString(R.string.max_rating, result.getVoteAverage()));

            txtOverview.setText(String.valueOf(result.getOverview()));
            txtRuntime.setText(String.valueOf(result.getRuntime()));
        }

        @Override
        public void onError(int networkStatusCode, Throwable cause) {

        }
    };

    private final VolleyFetch.Listener videosListener = new VolleyFetch.Listener() {
        @Override
        public void onResponse(JSONObject response) {

            Gson gson = new Gson();
            Trailer trailer = gson.fromJson(response.toString(), Trailer.class);

            rvTrailers.setAdapter(new TrailerListAdapter(trailer.getResults(), getContext()));

        }

        @Override
        public void onError(int networkStatusCode, Throwable cause) {

        }
    };

}
