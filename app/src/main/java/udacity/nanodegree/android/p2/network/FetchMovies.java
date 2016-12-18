package udacity.nanodegree.android.p2.network;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import udacity.nanodegree.android.p2.BuildConfig;
import udacity.nanodegree.android.p2.R;

/**
 * Created by alexandre on 13/11/2016.
 */

public class FetchMovies implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final FetchRules fetchRules;
    private final Context context;
    private final Listener listener;

    public FetchMovies(FetchRules fetchRules, Context context, Listener listener) {
        this.fetchRules = fetchRules;
        this.context = context;
        this.listener = listener;
    }

    public void execute() {
        Uri clientUri = fetchRules
                .composeUrl(Uri.parse(context.getString(R.string.tmdb_api_base_url)))
                .buildUpon()
                .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                .build();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, clientUri.toString(), null, this, this);

        RequestQueue queue = Volley.newRequestQueue(context);
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Throwable cause = error.getCause();
        if (error.networkResponse != null){
            listener.onError(error.networkResponse.statusCode, cause);
        }else {
            listener.onError(0, cause);
        }
    }

    public interface Listener {
        void onResponse(JSONObject response);

        void onError(int networkStatusCode, Throwable cause);

    }
}
