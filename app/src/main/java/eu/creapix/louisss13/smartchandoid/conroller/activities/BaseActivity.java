package eu.creapix.louisss13.smartchandoid.conroller.activities;

import android.content.Intent;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private TextView headerNavFullName, headerNavEmail;
    private boolean doubleBackToExitPressedOnce;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        this.headerNavFullName =  findViewById(R.id.nav_drawerheader_fullname);
        this.headerNavEmail = findViewById(R.id.nav_header_email);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        this.mNavigationView = findViewById(R.id.nav_view);
        this.mNavigationView.setNavigationItemSelectedListener(this);
        populatePeronalData();


    }

    private void populatePeronalData() {
        String firstNameTxt = PreferencesUtils.getFirstName(getApplicationContext());
        String lastNameTxt = PreferencesUtils.getLastname(getApplicationContext());
        String fullNameTxt = PreferencesUtils.getFirstName(getApplicationContext()) + " " + PreferencesUtils.getLastname(getApplicationContext());
        String emailTxt = PreferencesUtils.getEmail(getApplicationContext());

        View headerLayout = this.mNavigationView.getHeaderView(0);
        headerNavFullName = headerLayout.findViewById(R.id.nav_drawerheader_fullname);
        headerNavEmail = headerLayout.findViewById(R.id.nav_header_email);

        if ((!StringUtils.isEmpty(firstNameTxt)) && (!StringUtils.isEmpty(lastNameTxt)) &&  (headerNavFullName != null))
            if (headerNavFullName != null)
            headerNavFullName.setText(fullNameTxt);

        if ((!StringUtils.isEmpty(emailTxt)) &&(headerNavEmail != null )) {
            headerNavEmail.setText(emailTxt);
        }
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.press_back_to_quit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
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
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
