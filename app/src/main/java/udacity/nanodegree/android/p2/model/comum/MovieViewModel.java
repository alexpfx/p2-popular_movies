package udacity.nanodegree.android.p2.model.comum;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import udacity.nanodegree.android.p2.R;
import udacity.nanodegree.android.p2.model.movie.MoviesFragment;
import udacity.nanodegree.android.p2.network.data_transfer.Result;

/**
 * Created by alexandre on 27/11/2016.
 */

public class MovieViewModel extends BaseObservable {

    private MoviesFragment.OnMovieSelectedListener onMovieSelectedListener = MoviesFragment.OnMovieSelectedListener.EMPTY;

    private static final String TAG = "MovieViewModel";

    private Integer id;

    private String title;

    private String posterImage;

    private Date releaseDate;

    private Integer runtime;

    private Double voteAvg;

    private boolean favorite;

    private String synopsys;

    private Date updateDate;

    public MovieViewModel(MoviesFragment.OnMovieSelectedListener onMovieSelectedListener) {
        this.onMovieSelectedListener = onMovieSelectedListener;
    }

    public MovieViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        Log.d(TAG, "setPosterImage: " + posterImage);
        this.posterImage = posterImage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getSynopsys() {
        return synopsys;
    }

    public void setSynopsys(String synopsys) {
        this.synopsys = synopsys;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public static MovieViewModel fromResult(Result result) {
        MovieViewModel vm = new MovieViewModel();

        vm.setId(result.getId());
        vm.setTitle(result.getOriginalTitle());
        vm.setRuntime(result.getRuntime());
        vm.setSynopsys(result.getOverview());
        vm.setVoteAvg(result.getVoteAverage());
        vm.setPosterImage(result.getPosterPath());

        Calendar calendar;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(result.getReleaseDate()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        vm.setReleaseDate(calendar.getTime());
        vm.setUpdateDate(new Date());

        return vm;
    }

    public void onFavoriteClick(View v) {
        Checkable c = (Checkable) v;
        Log.d(TAG, "onFavoriteClick: " + c.isChecked());
    }

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void setImageUrl(ImageView view, String url) {
        Log.d(TAG, "setImageUrl: " + url);
        Picasso.with(view.getContext())
                .load(url)
                .error(R.drawable.ic_error_black_48dp)
                .into(view);
    }

    public void onClick(View v) {
        Log.d(TAG, "onItemClick: " + id);
        onMovieSelectedListener.onMovieSelected(this);
    }

    public void setOnMovieSelectedListener(MoviesFragment.OnMovieSelectedListener onMovieSelectedListener) {
        this.onMovieSelectedListener = onMovieSelectedListener;
    }

    @Override
    public String toString() {
        return "MovieViewModel{" +
                "synopsys='" + synopsys + '\'' +
                ", favorite=" + favorite +
                ", voteAvg=" + voteAvg +
                ", runtime=" + runtime +
                ", releaseDate=" + releaseDate +
                ", posterImage='" + posterImage + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
