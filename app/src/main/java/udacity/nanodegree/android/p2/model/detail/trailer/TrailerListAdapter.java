package udacity.nanodegree.android.p2.model.detail.trailer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import udacity.nanodegree.android.p2.databinding.ItemTrailerBinding;

/**
 * Created by alexandre on 15/11/2016.
 */
public class TrailerListAdapter extends RecyclerView.Adapter {
    private TrailerViewModelCollection trailers;
    private TrailerHandler.TrailerHandlerDelegate trailerHandlerDelegate;
    private Context context;
    private static final String TAG = "TrailerListAdapter";

    public TrailerListAdapter(Context context, TrailerViewModelCollection trailers) {
        this.trailers = trailers;
        this.context = context;
        this.trailerHandlerDelegate = (TrailerHandler.TrailerHandlerDelegate) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemTrailerBinding binding = ItemTrailerBinding.inflate(inflater, parent, false);
        return new TrailerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrailerViewHolder viewHolder = (TrailerViewHolder) holder;
        final TrailerViewModel trailerItem = trailers.get(position);
        viewHolder.bind(trailerItem, new TrailerHandler(trailerHandlerDelegate));
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        private ItemTrailerBinding binding;

        public void bind(TrailerViewModel item, TrailerHandler handler) {
            binding.setVm(item);
            binding.setHandler(handler);

        }

        public TrailerViewHolder(ItemTrailerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
