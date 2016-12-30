package udacity.nanodegree.android.p2.model.movie;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;

/**
 * Created by alexandre on 29/12/2016.
 */
public interface OnMovieSelectedListener {

    OnMovieSelectedListener EMPTY = new OnMovieSelectedListener() {
        @Override
        public void onMovieSelected(MovieViewModel item) {

        }
    };

    void onMovieSelected(MovieViewModel item);

}
