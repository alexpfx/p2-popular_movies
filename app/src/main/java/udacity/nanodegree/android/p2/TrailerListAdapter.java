package udacity.nanodegree.android.p2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import udacity.nanodegree.android.p2.domain.TrailerItem;

/**
 * Created by alexandre on 15/11/2016.
 */

public class TrailerListAdapter extends RecyclerView.Adapter {
    private final List<TrailerItem> trailers;

    public TrailerListAdapter(List<TrailerItem> trailers) {
        this.trailers = trailers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder {

        public TrailerViewHolder(View itemView) {
            super(itemView);
        }
    }

}
