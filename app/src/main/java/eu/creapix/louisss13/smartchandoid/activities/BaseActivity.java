package eu.creapix.louisss13.smartchandoid.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import eu.creapix.louisss13.smartchandoid.R;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        this.mNavigationView = findViewById(R.id.nav_view);
        this.mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupMenuSelection();
    }

    public void handleOnClick() {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    private void setupMenuSelection() {
        Menu menu = mNavigationView.getMenu();

        if (this instanceof MonitoredMatchesActivity) {
            MenuItem menuItem = menu.findItem(R.id.nav_monitor);
            menuItem.setChecked(true);
        } else if (this instanceof ProfileActivity) {
            MenuItem menuItem = menu.findItem(R.id.nav_profile);
            menuItem.setChecked(true);
        } else if (this instanceof MatchListActivity) {
            MenuItem menuItem = menu.findItem(R.id.nav_match_list);
            menuItem.setChecked(true);
        }

        // TODO not forget to do this for "configuration" & "contact"
    }

    public void goToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMatchList() {
        Intent intent = new Intent(this, MatchListActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMonitored() {
        Intent intent = new Intent(this, MonitoredMatchesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            if (!(this instanceof ProfileActivity))
                goToProfile();
        } else if (id == R.id.nav_monitor) {
            if (!(this instanceof MonitoredMatchesActivity))
                goToMonitored();
        } else if (id == R.id.nav_match_list) {
            if (!(this instanceof MatchListActivity))
                goToMatchList();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
