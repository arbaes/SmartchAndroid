package eu.creapix.louisss13.smartchandoid.conroller.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.conroller.adapter.BrowseListAdapter;
import eu.creapix.louisss13.smartchandoid.dataAccess.TournamentsDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

public class MatchListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, WebserviceListener {


    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private BrowseListAdapter browseListAdapter;
    private TabLayout tabLayout;
    private Context classContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_tournaments);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_tournaments);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryLightColor, R.color.primaryColor, R.color.primaryDarkColor);

        setupTabLayout();
        setupContentForTab();
    }

    private void setupTabLayout() {
        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tournaments));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.matches));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setupContentForTab();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    private void setupContentForTab() {
        swipeRefreshLayout.setRefreshing(true);
        if (Utils.hasConnexion(getApplicationContext())) {
            new GetDatas().execute();
        } else {
            Toast.makeText(MatchListActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void setupDatasAfterFetch(ArrayList<Object> datas) {
        findViewById(R.id.empty).setVisibility(View.GONE);
        browseListAdapter = new BrowseListAdapter(MatchListActivity.this, datas);
        recyclerView.setAdapter(browseListAdapter);

        if (datas == null || datas.size() == 0) {
            findViewById(R.id.empty).setVisibility(View.VISIBLE);
        }


        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onWebserviceFinishWithSuccess(final String method, Integer id, final ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (method.equals(Constants.GET_TOURNAMENT) || method.equals(Constants.GET_MATCHES) || method.equals(Constants.GET_WATCHED)) {
                    setupDatasAfterFetch(datas);
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
                switch (errorCode) {
                    case 401:
                        Utils.alertSessionExpired(MatchListActivity.this);
                        break;
                    default:
                        Utils.alertError(MatchListActivity.this,getString(R.string.server_error_title), getString(R.string.server_error_content));
                        break;
                }
            }
        });

    }

    private class GetDatas extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // TODO create dao function fort matches
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        TournamentsDao tournamentsDao = new TournamentsDao();
                        tournamentsDao.getTournaments(MatchListActivity.this, PreferencesUtils.getToken(getApplicationContext()));
                        break;
                    case 1:
                        onWebserviceFinishWithSuccess(Constants.GET_MATCHES, 0, new ArrayList<Object>());
                        break;
                }
                return true;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!(success)) {
                Utils.alertError(MatchListActivity.this, getString(R.string.error_connection_lost_title),getString(R.string.error_connection_lost_content));
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
        new GetDatas().execute();
    }

    @Override
    public void onRefresh() {
        if (Utils.hasConnexion(getApplicationContext())) {
            refresh();
        } else {
            Toast.makeText(MatchListActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}