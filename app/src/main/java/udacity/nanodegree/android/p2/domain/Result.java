package udacity.nanodegree.android.p2.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@SuppressWarnings("unused")
@Generated("org.jsonschema2pojo")
public class Result {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;


    public String getPosterPath() {
        return posterPath;
    }


    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public String getOverview() {
        return overview;
    }


    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }


    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }


    public String getOriginalLanguage() {
        return originalLanguage;
    }


    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public Double getPopularity() {
        return popularity;
    }


    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }


    public Integer getVoteCount() {
        return voteCount;
    }


    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }


    public Double getVoteAverage() {
        return voteAverage;
    }


    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }


}
