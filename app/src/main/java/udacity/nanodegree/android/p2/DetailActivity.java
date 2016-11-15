package udacity.nanodegree.android.p2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import udacity.nanodegree.android.p2.domain.Result;
import udacity.nanodegree.android.p2.domain.Trailer;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new DetailFragment()).commit();
        }

    }


    public static class DetailFragment extends Fragment {

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

            initRecyclerView ();

            String id = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
            new VolleyFetchMovies(new GetVideos(id), getContext(), videosListener).execute();
            new VolleyFetchMovies(new GetMovie(id), getContext(), movieDetailListener).execute();
            return view;
        }

        private void initRecyclerView() {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
            rvTrailers.setLayoutManager(layoutManager);
        }


        private final VolleyFetchMovies.Listener movieDetailListener = new VolleyFetchMovies.Listener() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Result result = gson.fromJson(response.toString(), Result.class);

                txtTitle.setText(result.getOriginalTitle());
                txtReleaseDate.setText(result.getReleaseDate());
                String path = getString(R.string.tmdb_image_base_path) + result.getPosterPath();

                Picasso.with(getContext()).load(path).placeholder(R.drawable.loading).error(R.drawable.error).into(imgPoster);
                Calendar calendar;
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.US);
                    calendar = Calendar.getInstance();
                    calendar.setTime(simpleDateFormat.parse(result.getReleaseDate()));

                } catch (ParseException e) {
                    Log.e(TAG, "parse exception: ", e);
                    throw new RuntimeException(e);
                }

                txtReleaseDate.setText(String.valueOf(calendar.get(Calendar.YEAR)));
                txtVoteAvg.setText(String.valueOf(result.getVoteAverage()));
                txtOverview.setText(String.valueOf(result.getOverview()));
            }

            @Override
            public void onError(int networkStatusCode, Throwable cause) {

            }
        };

        private final VolleyFetchMovies.Listener videosListener = new VolleyFetchMovies.Listener() {
            @Override
            public void onResponse(JSONObject response) {

                Gson gson = new Gson();
                Trailer trailer = gson.fromJson(response.toString(), Trailer.class);

                Log.d(TAG, "onResponse: "+trailer);
                rvTrailers.setAdapter(new TrailerListAdapter(trailer.getResults(), getContext()));

            }

            @Override
            public void onError(int networkStatusCode, Throwable cause) {

            }
        };

    }


}
