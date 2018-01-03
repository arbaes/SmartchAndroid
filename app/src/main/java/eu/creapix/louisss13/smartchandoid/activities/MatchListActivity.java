package eu.creapix.louisss13.smartchandoid.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.adapter.BrowseListAdapter;
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
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryLightColor, R.color.primaryColor, R.color.primaryDarkColor);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        setupTabLayout();
        setupContentForTab();
    }

    private void setupTabLayout() {
        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setText("Tournaments"));
        tabLayout.addTab(tabLayout.newTab().setText("Matches"));
        tabLayout.addTab(tabLayout.newTab().setText("Watched"));


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
        new GetDatas().execute();
    }

    private void setupDatasAfterFetch(ArrayList<Object> datas) {
        browseListAdapter = new BrowseListAdapter(MatchListActivity.this, datas);
        recyclerView.setAdapter(browseListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onWebserviceFinishWithSuccess(final String method, final ArrayList<Object> datas) {
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
    public void onWebserviceFinishWithError(String error) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MatchListActivity.this);

                // set title
                alertDialogBuilder.setTitle(R.string.title_session_expired);

                // set dialog message
                alertDialogBuilder
                        .setMessage(R.string.content_session_expired)
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                Intent intent = new Intent(MatchListActivity.this, LoginActivity.class);
                                startActivity(intent);
                                MatchListActivity.this.finish();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                Toast.makeText(MatchListActivity.this, "An internet connection is required for this operation", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class GetDatas extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // TODO create dao function fort matches and watched
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        TournamentsDao tournamentsDao = new TournamentsDao();
                        tournamentsDao.getTournaments(MatchListActivity.this, PreferencesUtils.getToken(getApplicationContext()));
                        break;
                    case 1:
                        onWebserviceFinishWithSuccess(Constants.GET_MATCHES, new ArrayList<Object>());
                        break;
                    case 2:
                        onWebserviceFinishWithSuccess(Constants.GET_WATCHED, new ArrayList<Object>());
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
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
            Toast.makeText(MatchListActivity.this, "An internet connection is required for this operation", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
