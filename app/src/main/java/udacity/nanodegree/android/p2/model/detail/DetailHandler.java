package udacity.nanodegree.android.p2.model.detail;

import android.view.View;
import android.widget.Checkable;

import udacity.nanodegree.android.p2.model.comum.MovieViewModel;

/**
 * Created by alexandre on 11/12/2016.
 */

public class DetailHandler {

    private DetailHandlerDelegate delegate;

    public DetailHandler(DetailHandlerDelegate delegate) {
        this.delegate = delegate;
    }

    public void onFavoriteClick(View v, MovieViewModel viewModel) {
        if (delegate == null) {
            return;
        }
        delegate.onFavorite(((Checkable) v).isChecked(), viewModel);

    }

    public interface DetailHandlerDelegate {
        void onFavorite(boolean isFavorited, MovieViewModel viewModel);
    }

}