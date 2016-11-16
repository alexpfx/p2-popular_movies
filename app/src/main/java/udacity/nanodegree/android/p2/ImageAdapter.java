package udacity.nanodegree.android.p2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexandre on 25/10/2016.
 */

public class ImageAdapter extends ArrayAdapter<ImageAdapter.Item> {


    public ImageAdapter(List<Item> imagePaths, Context context) {
        super(context, 0, imagePaths);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();


        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_poster, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridView gridView = (GridView) parent;
        gridView.setColumnWidth((gridView.getWidth()) / 2);

        Item item = getItem(position);
        String path = context.getString(R.string.tmdb_image_base_path) + item.getPath();

        Picasso.with(context).load(path).placeholder(R.drawable.ic_autorenew_black_48dp).error(R.drawable.ic_error_black_48dp).into(holder.posterImage);

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.poster_image)
        ImageView posterImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    static class Item {
        public Item(int id, String path) {
            this.id = id;
            this.path = path;
        }

        private final int id;
        private final String path;

        public int getId() {
            return id;
        }

        public String getPath() {
            return path;
        }

        @Override
        public String toString() {
            return "" + id;
        }
    }

}
