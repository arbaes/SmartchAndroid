package eu.creapix.louisss13.smartchandoid.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.MonitoredMatchesDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.adapter.MonitoredMatchAdapter;
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);


        swipeRefreshLayout.setColorSchemeResources(R.color.primaryLightColor, R.color.primaryColor, R.color.primaryDarkColor);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setRefreshing(true);
        new GetMonitoredMatches().execute();


    }

    public void monitorMatch(int matchId, PlayerScore matchScore) {
        Intent intent = new Intent(this, PointCountActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(Constants.MATCH_ID, matchId);
        intent.putExtra(Constants.MATCH_SCORE, matchScore);
        startActivity(intent);
    }

    public void populateMatches(ArrayList<Object> matchs){

        monitoredMatchAdapter = new MonitoredMatchAdapter(MonitoredMatchesActivity.this, matchs);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(monitoredMatchAdapter);

    }

    private class GetMonitoredMatches extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                MonitoredMatchesDao monitoredMatchesDao = new MonitoredMatchesDao();
                monitoredMatchesDao.getMonitoredMatches(MonitoredMatchesActivity.this, PreferencesUtils.getToken(getApplicationContext()));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private void refresh() {
        new GetMonitoredMatches().execute();
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
    public void onWebserviceFinishWithSuccess(final String method, final ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (method.equals(Constants.GET_MONITORED_MATCHES) && (datas.size() > 0)) {

                    if (datas.get(0) instanceof MatchParser) {

                         populateMatches(datas);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error) {
        swipeRefreshLayout.setRefreshing(false);
    }

}