package udacity.nanodegree.android.p2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.nanodegree.android.p2.domain.TrailerItem;

/**
 * Created by alexandre on 15/11/2016.
 */

public class TrailerListAdapter extends RecyclerView.Adapter {
    private final List<TrailerItem> trailers;
    private Context context;


    public TrailerListAdapter(List<TrailerItem> trailers, Context context) {
        this.trailers = trailers;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trailer, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrailerViewHolder viewHolder = (TrailerViewHolder) holder;
        final TrailerItem trailerItem = trailers.get(position);
        viewHolder.txtTrailerTitle.setText(trailerItem.getName());

        viewHolder.txtTrailerTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = trailerItem.getKey();
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));

                if (appIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(appIntent);
                } else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key));
                    context.startActivity(webIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_play_trailer)
        ImageButton btnPlayTrailer;

        @BindView(R.id.text_trailer_title)
        TextView txtTrailerTitle;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
