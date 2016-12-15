package udacity.nanodegree.android.p2.model.detail;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;

/**
 * Created by alexandre on 13/12/2016.
 */

public class MovieLoader extends AsyncTaskLoader<MovieViewModel> {
    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    public MovieViewModel loadInBackground() {
        return null;
    }
}
