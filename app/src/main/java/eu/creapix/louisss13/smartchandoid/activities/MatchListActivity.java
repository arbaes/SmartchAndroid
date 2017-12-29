package eu.creapix.louisss13.smartchandoid.activities;

import android.support.design.widget.TabLayout;

import android.os.Bundle;

import android.widget.TextView;

import eu.creapix.louisss13.smartchandoid.R;

public class MatchListActivity extends BaseActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupTabLayout();
        setupContentForTab(0);
    }

    private void setupTabLayout() {
        tabLayout.removeAllTabs();

        tabLayout.addTab(tabLayout.newTab().setText("Tournaments"));
        tabLayout.addTab(tabLayout.newTab().setText("Matches"));
        tabLayout.addTab(tabLayout.newTab().setText("Watched"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setupContentForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO scroll top
            }
        });
    }

    private void setContentForTournaments() {

    }

    private void setContentForMatches() {

    }

    private void setContentForWatched() {

    }

    private void setupContentForTab(int position) {
        TextView textView = (TextView) findViewById(R.id.text);
        switch (position) {
            case 0:
                textView.setText("TOURNAMENTS");
                setContentForTournaments();
                break;
            case 1:
                textView.setText("MATCHES");
                setContentForMatches();
                break;
            case 2:
                textView.setText("WATCHED");
                setContentForWatched();
                break;
        }
    }
}
