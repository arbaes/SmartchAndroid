package eu.creapix.louisss13.smartchandoid.activities;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.dataAccess.ClubsDao;
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
    public void onWebserviceFinishWithSuccess(String method, final ArrayList<Object> datas) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((datas != null) && (datas.get(0) instanceof AccountParser)) {
                    AccountParser account = (AccountParser) datas.get(0);
                    ViewUserDetailsActivity.this.userId = account.getUserInfos()[0].getId();
                    UserInfoParser[] userInfo = account.getUserInfos();
                    if ( userInfo.length > 0 ) {
                        ViewUserDetailsActivity.this.userInfos = userInfo;
                        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
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
    public void onWebserviceFinishWithError(String error) {

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(String fullname, String address, String phone, String birthdate, String age, String email) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("ARG_FULLNAME", fullname);
            args.putString("ARG_SHORT_ADDRESS", address);
            args.putString("ARG_PHONE", phone);
            args.putString("ARG_BIRTHDATE", birthdate);
            args.putString("ARG_AGE", age);
            args.putString("ARG_EMAIL", email);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_user_details, container, false);
            TextView fullname = (TextView) rootView.findViewById(R.id.user_data_fullname);
            TextView address = rootView.findViewById(R.id.user_data_address);
            TextView phone = rootView.findViewById(R.id.user_data_phone);
            TextView birthdate = rootView.findViewById(R.id.user_data_birthdate);
            TextView age = rootView.findViewById(R.id.user_data_age);
            TextView email = rootView.findViewById(R.id.user_data_email);

            fullname.setText(""+getArguments().getString("ARG_FULLNAME"));
            address.setText(""+getArguments().getString("ARG_SHORT_ADDRESS"));
            phone.setText(""+getArguments().get("ARG_PHONE"));
            birthdate.setText(""+getArguments().get("ARG_BIRTHDATE"));
            age.setText(""+getArguments().get("ARG_AGE"));
            email.setText(""+getArguments().get("ARG_EMAIL"));
            return rootView;
        }
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

    private class GetClubs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                ClubsDao profileDao = new ClubsDao();
                profileDao.getClubs(ViewUserDetailsActivity.this, ""+ViewUserDetailsActivity.this.userId,PreferencesUtils.getToken(getApplicationContext()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Fragment getItem(int position) {

            String fullname = ""+ ViewUserDetailsActivity.this.userInfos[position].getFirstName() +" "+ ViewUserDetailsActivity.this.userInfos[position].getLastName();
            String address = "";
            if (ViewUserDetailsActivity.this.userInfos[position].getAddress() != null) {
                address = ViewUserDetailsActivity.this.userInfos[position].getAddress().getShortAdress();
            }
            String phone = ""+ViewUserDetailsActivity.this.userInfos[position].getPhone();
            String birthDate = ""+ViewUserDetailsActivity.this.userInfos[position].getParsedBirthDate();
            String birthDateBrut = ""+ViewUserDetailsActivity.this.userInfos[position].getBirthDate();
            ViewUserDetailsActivity.this.userId = ViewUserDetailsActivity.this.userInfos[position].getId();
            new GetClubs().execute();

            Calendar today = new GregorianCalendar();
            Calendar birthDateCal = Calendar.getInstance();

            int age = 0;

            SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date convertedDate = new Date();
            try {
                convertedDate = StringToDate.parse(birthDateBrut);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            birthDateCal.setTime(convertedDate);
            age = today.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);

            String email = ""+""+ViewUserDetailsActivity.this.userInfos[position].getEmail();

            return PlaceholderFragment.newInstance("" + fullname, ""+address,""+phone,""+birthDate, ""+age,""+email);

        }

        @Override
        public int getCount() {
            return ViewUserDetailsActivity.this.userInfos.length;
        }
    }
}
