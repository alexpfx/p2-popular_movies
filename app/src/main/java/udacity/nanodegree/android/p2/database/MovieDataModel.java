package udacity.nanodegree.android.p2.database;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alexandre on 22/11/2016.
 */

@Table(name = "Movies")
public class MovieDataModel {
    private MovieDataModel() {
    }

    @Id
    @Column
    private Integer id;

    @Column
    private String title;

    @Column
    private String poster;

    @Column
    private String synopsys;

    @Column
    private Double userRating;

    @Column
    private String releaseDate;

    private Double voteAvg;

    private Integer runtime;

    private boolean favorite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsys() {
        return synopsys;
    }

    public void setSynopsys(String synopsys) {
        this.synopsys = synopsys;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
