package udacity.nanodegree.android.p2.model.detail;

/**
 * Created by alexandre on 11/12/2016.
 */

public class TrailerHandler {

    private TrailerHandlerDelegate delegate;

    public TrailerHandler(TrailerHandlerDelegate delegate) {
        this.delegate = delegate;
    }

    public void onItemClick(TrailerViewModel vm) {
        if (delegate == null) {
            return;
        }
        delegate.onTrailerPlay(vm.getKey());

    }

    public interface TrailerHandlerDelegate {
        void onTrailerPlay(String key);
    }
}
