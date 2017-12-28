package eu.creapix.louisss13.smartchandoid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.Model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.adapter.MonitoredMatchAdapter;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

/**
 * Created by arnau on 21-12-17.
 */

public class MonitoredMatchesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MonitoredMatchAdapter monitoredMatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitored_matches);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        final ArrayList<PlayerScore> playerScores = new ArrayList<PlayerScore>();
        for (int i = 1; i <= 20; i++) {
            playerScores.add(new PlayerScore("Joueur" + i, i + 7));
        }

        monitoredMatchAdapter = new MonitoredMatchAdapter(MonitoredMatchesActivity.this, playerScores);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(monitoredMatchAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.primaryLightColor, R.color.primaryColor, R.color.primaryDarkColor);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void monitorMatch() {
        Intent intent = new Intent(this, PointCountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void refresh() {
        // TODO call to webservice
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        handler.postDelayed(r, 2000);
    }

    @Override
    public void onRefresh() {
        if (Utils.hasConnexion(getApplicationContext())) {
            refresh();
        } else {
            Toast.makeText(MonitoredMatchesActivity.this, "An internet connection is required for this operation", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /*
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }*/

}