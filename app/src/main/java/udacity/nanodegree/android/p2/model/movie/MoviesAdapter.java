package udacity.nanodegree.android.p2.model.movie;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import udacity.nanodegree.android.p2.databinding.ItemPosterBinding;
import udacity.nanodegree.android.p2.model.comum.MovieViewModel;

/**
 * Created by alexandre on 28/11/2016.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private static final String TAG = "MoviesAdapter";
    private List<MovieViewModel> movies;

    public MoviesAdapter(List<MovieViewModel> movies) {
        this.movies = movies;
    }

    public void setMovies(List<MovieViewModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPosterBinding binding = ItemPosterBinding.inflate(inflater, parent, false);

        return new MoviesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ItemPosterBinding binding;

        public void bind(MovieViewModel viewModel) {
            binding.setVm(viewModel);

        }

        public MoviesViewHolder(ItemPosterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
