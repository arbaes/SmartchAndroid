package eu.creapix.louisss13.smartchandoid.conroller.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.conroller.adapter.MonitoredMatchAdapter;
import eu.creapix.louisss13.smartchandoid.dataAccess.MonitoredMatchesDao;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ScoreCalculatedParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
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

        recyclerView = findViewById(R.id.recycler);
        findViewById(R.id.no_data_view).setVisibility(View.GONE);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryLightColor, R.color.primaryColor, R.color.primaryDarkColor);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);

        if (Utils.hasConnexion(getApplicationContext())) {
            new GetMonitoredMatches().execute();
        } else {
            Toast.makeText(MonitoredMatchesActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }


    }

    public void monitorMatch(int matchId, ScoreCalculatedParser matchScore, String player1DisplayName, String player2DisplayName) {
        Intent intent = new Intent(this, PointCountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Constants.MATCH_ID, matchId);
        intent.putExtra(Constants.MATCH_SCORE, matchScore);
        intent.putExtra(Constants.PLAYER_1_NAME, player1DisplayName);
        intent.putExtra(Constants.PLAYER_2_NAME, player2DisplayName);
        startActivityForResult(intent, Constants.RESULT_COUNT_POINT);
    }

    public void populateMatches(ArrayList<Object> matchs) {

        monitoredMatchAdapter = new MonitoredMatchAdapter(MonitoredMatchesActivity.this, matchs);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext()/*, LinearLayoutManager.HORIZONTAL, false*/);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(monitoredMatchAdapter);
        swipeRefreshLayout.setRefreshing(false);

    }

    private class GetMonitoredMatches extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                MonitoredMatchesDao monitoredMatchesDao = new MonitoredMatchesDao();
                monitoredMatchesDao.getMonitoredMatches(MonitoredMatchesActivity.this, PreferencesUtils.getToken(getApplicationContext()));
                return true;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!(success)) {
                Utils.alertError(MonitoredMatchesActivity.this, getString(R.string.error_connection_lost_title), getString(R.string.error_connection_lost_content));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }
    }

    private void refresh() {
        new GetMonitoredMatches().execute();
    }

    @Override
    public void onRefresh() {
        findViewById(R.id.no_data_view).setVisibility(View.GONE);
        if (Utils.hasConnexion(getApplicationContext())) {
            refresh();
        } else {
            Toast.makeText(MonitoredMatchesActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onWebserviceFinishWithSuccess(final String method, Integer id, final ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (method.equals(Constants.GET_MONITORED_MATCHES) && (datas.size() > 0)) {
                    if (datas.get(0) instanceof MatchParser) {
                        populateMatches(datas);
                    }
                    findViewById(R.id.no_data_view).setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error, final int errorCode) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                switch (errorCode) {
                    case 401:
                        Utils.alertSessionExpired(MonitoredMatchesActivity.this);
                        break;
                    default:
                        Utils.alertError(MonitoredMatchesActivity.this, getString(R.string.server_error_title), getString(R.string.server_error_content));
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.RESULT_COUNT_POINT && resultCode == RESULT_OK) {
            if (Utils.hasConnexion(getApplicationContext())) {
                swipeRefreshLayout.setRefreshing(true);
                refresh();
            } else {
                Toast.makeText(MonitoredMatchesActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}