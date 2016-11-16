package udacity.nanodegree.android.p2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import udacity.nanodegree.android.p2.detail.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new DetailFragment()).commit();
        }

    }


}
