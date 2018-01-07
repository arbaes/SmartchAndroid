package eu.creapix.louisss13.smartchandoid.conroller.activities;

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
import eu.creapix.louisss13.smartchandoid.conroller.adapter.SectionsPagerAdapter;
import eu.creapix.louisss13.smartchandoid.dataAccess.ProfileDao;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.AccountParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.UserInfoParser;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Utils.hasConnexion(getApplicationContext())) {
            new GetDatas().execute();
        } else {
            Toast.makeText(ViewUserDetailsActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
        }

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
                        mViewPager = findViewById(R.id.container);
                        mViewPager.setAdapter(mSectionsPagerAdapter);
                    } else {
                        Toast.makeText(ViewUserDetailsActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                    }
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
                        Utils.alertSessionExpired(ViewUserDetailsActivity.this);
                        break;
                    default:
                        Utils.alertError(ViewUserDetailsActivity.this,getString(R.string.server_error_title), getString(R.string.server_error_content));
                        break;
                }
            }
        });
    }

    private class GetDatas extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                ProfileDao profileDao = new ProfileDao();
                profileDao.getProfile(ViewUserDetailsActivity.this, PreferencesUtils.getToken(getApplicationContext()));
                return true;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!(success)) {
                Utils.alertError(ViewUserDetailsActivity.this, getString(R.string.error_connection_lost_title),getString(R.string.error_connection_lost_content));
            }
        }
    }
}
