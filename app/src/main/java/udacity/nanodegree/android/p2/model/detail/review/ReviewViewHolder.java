package udacity.nanodegree.android.p2.model.detail.review;

import android.support.v7.widget.RecyclerView;

import udacity.nanodegree.android.p2.databinding.ItemReviewBinding;

/**
 * Created by alexandre on 18/12/2016.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private ItemReviewBinding binding;

    public ReviewViewHolder(ItemReviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ReviewViewModel vm) {
        binding.setVm(vm);

    }
}
