package udacity.nanodegree.android.p2.model.comum;

import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_IS_FAVORITE;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_MOVIE_ID;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_POSTER;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_RELEASE_DATE;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_RUNTIME;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_SYNOPSIS;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_TITLE;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_UPDATE_DATE;
import static udacity.nanodegree.android.p2.database.MoviesContract.MovieEntry.INDEX_USER_RATING;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.databinding.BindingAdapter;
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
import udacity.nanodegree.android.p2.database.MoviesContract;
import udacity.nanodegree.android.p2.model.movie.OnMovieSelectedListener;
import udacity.nanodegree.android.p2.network.data_transfer.Result;

/**
 * Created by alexandre on 27/11/2016.
 */

public class MovieViewModel {


    private static final String TAG = "MovieViewModel";
    private OnMovieSelectedListener onMovieSelectedListener = OnMovieSelectedListener.EMPTY;
    private Integer id;

    private String title;

    private String posterImage;

    private Date releaseDate;

    private Integer runtime;

    private Double voteAvg;

    private boolean favorite;

    private String synopsys;

    private Date updateDate;

    MovieViewModel(Integer id, String title, String posterImage, Date releaseDate, Integer runtime,
            Double voteAvg, boolean favorite, String synopsys, Date updateDate) {
        this.id = id;
        this.title = title;
        this.posterImage = posterImage;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.voteAvg = voteAvg;
        this.favorite = favorite;
        this.synopsys = synopsys;
        this.updateDate = updateDate;
    }

    public MovieViewModel(OnMovieSelectedListener onMovieSelectedListener) {
        this.onMovieSelectedListener = onMovieSelectedListener;
    }

    public MovieViewModel() {
    }

    public static final MovieViewModel fromCursor(Cursor cursor) {
        Builder builder = new Builder();
        String title = cursor.getString(INDEX_TITLE);
        String poster = cursor.getString(INDEX_POSTER);
        String synopsis = cursor.getString(INDEX_SYNOPSIS);

        int movieId = cursor.getInt(INDEX_MOVIE_ID);
        int runtime = cursor.getInt(INDEX_RUNTIME);

        long isFavorite = cursor.getLong(INDEX_IS_FAVORITE);
        long release_date = cursor.getLong(INDEX_RELEASE_DATE);
        long update_date = cursor.getLong(INDEX_UPDATE_DATE);

        double user_rating = cursor.getDouble(INDEX_USER_RATING);

        return builder.setTitle(title)
                .setPosterImage(poster)
                .setSynopsys(synopsis)
                .setFavorite(isFavorite == 1L)
                .setReleaseDate(new Date(release_date))
                .setUpdateDate(new Date(update_date))
                .setId(movieId)
                .setRuntime(runtime)
                .setVoteAvg(user_rating)
                .build();
    }

    public static MovieViewModel fromResult(Result result) {
        Builder builder = new Builder();
        Calendar calendar;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(result.getReleaseDate()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return builder.setId(result.getId())
                .setTitle(result.getOriginalTitle())
                .setRuntime(result.getRuntime())
                .setSynopsys(result.getOverview())
                .setVoteAvg(result.getVoteAverage())
                .setPosterImage(result.getPosterPath())
                .setReleaseDate(calendar.getTime())
                .setUpdateDate(new Date())
                .build();

    }

    @BindingAdapter(value = {"imageUrl"}, requireAll = false)
    public static void setImageUrl(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .error(R.drawable.ic_error_black_48dp)
                .into(view);

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
        this.posterImage = posterImage;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String formatRuntime (Context context){
        return runtime == null? null: context.getString(R.string.min, runtime);
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

//    android:text="@{@string/max_rating(vm.voteAvg)?? ``}"
    public String formatVoteAvg (Context context){
        return voteAvg == null? null:context.getString(R.string.max_rating, voteAvg);
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

    public String formatReleaseDate (Context context){
        return releaseDate == null? null: context.getString(R.string.dateToYear, releaseDate);
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void onClick(View v) {
        onMovieSelectedListener.onMovieSelected(this);
    }

    public void setOnMovieSelectedListener(OnMovieSelectedListener onMovieSelectedListener) {
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

    //TODO move
    public ContentValues createContentValues() {
        ContentValues c = new ContentValues();
        c.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, getId());
        c.put(MoviesContract.MovieEntry.COLUMN_POSTER, getPosterImage());
        c.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, getReleaseDate()
                .getTime());
        c.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS, getSynopsys());
        c.put(MoviesContract.MovieEntry.COLUMN_TITLE, getTitle());
        c.put(MoviesContract.MovieEntry.COLUMN_USER_RATING, getVoteAvg());
        c.put(MoviesContract.MovieEntry.COLUMN_IS_FAVORITE, isFavorite() ? 1 : 0);
        c.put(MoviesContract.MovieEntry.COLUMN_UPDATE_DATE, getUpdateDate()
                .getTime());
        c.put(MoviesContract.MovieEntry.COLUMN_RUNTIME, getRuntime());

        return c;
    }
}
