package eu.creapix.louisss13.smartchandoid.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.UsersDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.adapter.MonitoredMatchAdapter;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

/**
 * Created by arnau on 21-12-17.
 */

public class MonitoredMatchesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, WebserviceListener {

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
        new GetScore().execute();
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

    @Override
    public void onWebserviceFinishWithSuccess(final String method, ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (method.equals(Constants.GET_SCORE))
                    swipeRefreshLayout.setRefreshing(false);
                // TODO refresh adapter with new datas
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error) {
        swipeRefreshLayout.setRefreshing(false);
    }

    private class GetScore extends AsyncTask<Void, Void, Boolean> {
        private UsersDao userDao;

        GetScore() {
            userDao = new UsersDao();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                userDao.getScores(MonitoredMatchesActivity.this, "token");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}