package udacity.nanodegree.android.p2.model.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import udacity.nanodegree.android.p2.R;
import udacity.nanodegree.android.p2.network.data_transfer.TrailerItem;

/**
 * Created by alexandre on 27/11/2016.
 */

public class TrailerViewModel extends BaseObservable {
    private String id;
    private String key;
    private String title;
    private static final String TAG = "TrailerViewModel";

    public TrailerViewModel(String id, String key, String title) {
        this.id = id;
        this.key = key;
        this.title = title;
    }

    public static TrailerViewModel fromTrailerItem(TrailerItem item) {
        return new TrailerViewModel(item.getId(), item.getKey(), item.getName());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void onItemClick(View view) {
        Log.d(TAG, "onItemClick: " + key);
        Context context = view.getContext();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.youtube_app, key)));

        if (appIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(appIntent);
        } else {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.youtube_web, key)));
            context.startActivity(webIntent);
        }

    }

}
