package udacity.nanodegree.android.p2.model.comum;

import java.util.Date;

import udacity.nanodegree.android.p2.model.movie.OnMovieSelectedListener;

public class Builder {
    private Integer id;
    private String title;
    private String posterImage;
    private Date releaseDate;
    private Integer runtime;
    private Double voteAvg;
    private boolean favorite;
    private String synopsys;
    private Date updateDate;
    private OnMovieSelectedListener onMovieSelectedListener;

    public Builder setId(Integer id) {
        this.id = id;
        return this;
    }

    public Builder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Builder setPosterImage(String posterImage) {
        this.posterImage = posterImage;
        return this;
    }

    public Builder setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Builder setRuntime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    public Builder setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
        return this;
    }

    public Builder setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public Builder setSynopsys(String synopsys) {
        this.synopsys = synopsys;
        return this;
    }

    public Builder setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Builder setOnMovieSelectedListener(OnMovieSelectedListener onMovieSelectedListener) {
        this.onMovieSelectedListener = onMovieSelectedListener;
        return this;
    }

    public MovieViewModel build() {
        return new MovieViewModel(id, title, posterImage, releaseDate, runtime, voteAvg, favorite, synopsys, updateDate);
    }
}