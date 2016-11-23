package udacity.nanodegree.android.p2.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alexandre on 22/11/2016.
 */

@Table(name = "Movies")
public class MovieData {
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
    private Date releaseDate;


}
