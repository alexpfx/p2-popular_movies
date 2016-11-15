package udacity.nanodegree.android.p2;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by alexandre on 13/11/2016.
 */

public class VolleyFetch implements Response.Listener<JSONObject>, Response.ErrorListener {
    private static final String URL_BASE = "https://api.themoviedb.org/3/movie/?";

    private final FetchRules fetchRules;
    private final Context context;
    private final Listener listener;

    public VolleyFetch(FetchRules fetchRules, Context context, Listener listener) {
        this.fetchRules = fetchRules;
        this.context = context;
        this.listener = listener;
    }


    public void execute() {
        Uri clientUri = fetchRules
                .composeUrl(Uri.parse(URL_BASE))
                .buildUpon()
                .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                .build();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, clientUri.toString(), null, this, this);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        listener.onError(error.networkResponse.statusCode, error.getCause());
    }

    public interface Listener {
        void onResponse(JSONObject response);

        void onError(int networkStatusCode, Throwable cause);

    }
}
