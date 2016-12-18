package udacity.nanodegree.android.p2.model.detail.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import udacity.nanodegree.android.p2.databinding.ItemReviewBinding;
import udacity.nanodegree.android.p2.model.comum.ViewModelCollection;
import udacity.nanodegree.android.p2.network.data_transfer.ReviewItem;

/**
 * Created by alexandre on 18/12/2016.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private Context context;
    private ViewModelCollection<ReviewViewModel, ReviewItem> reviews;

    public ReviewListAdapter(Context context, ViewModelCollection<ReviewViewModel, ReviewItem> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemReviewBinding inflate = ItemReviewBinding.inflate(inflater, parent, false);
        return new ReviewViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        ReviewViewModel review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
