package eu.creapix.louisss13.smartchandoid.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.adapter.SectionsPagerAdapter;
import eu.creapix.louisss13.smartchandoid.dataAccess.ProfileDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.AccountParser;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.UserInfoParser;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

public class ViewUserDetailsActivity extends AppCompatActivity implements WebserviceListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private UserInfoParser[] userInfos;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_view_user_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new GetDatas().execute();

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public void onWebserviceFinishWithSuccess(String method, Integer id, final ArrayList<Object> datas) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((datas != null) && (datas.get(0) instanceof AccountParser)) {
                    AccountParser account = (AccountParser) datas.get(0);
                    ViewUserDetailsActivity.this.userId = account.getUserInfos()[0].getId();
                    UserInfoParser[] userInfo = account.getUserInfos();
                    if ( userInfo.length > 0 ) {
                        ViewUserDetailsActivity.this.userInfos = userInfo;
                        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), userInfo);
                        mViewPager = (ViewPager) findViewById(R.id.container);
                        mViewPager.setAdapter(mSectionsPagerAdapter);
                    } else {
                        Toast.makeText(ViewUserDetailsActivity.this, "Aucune donnée de profil trouvée", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error, int errorCode) {

    }


    public void populateUserData(){

    }

    private class GetDatas extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                ProfileDao profileDao = new ProfileDao();
                profileDao.getProfile(ViewUserDetailsActivity.this, PreferencesUtils.getToken(getApplicationContext()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
