package eu.creapix.louisss13.smartchandoid.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.activities.ViewUserDetailsActivity;
import eu.creapix.louisss13.smartchandoid.adapter.ClubListAdapter;
import eu.creapix.louisss13.smartchandoid.dataAccess.ClubsDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.ClubParser;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.UserInfoParser;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

/**
 * Created by arnau on 05-01-18.
 */

public class PlaceholderFragment extends Fragment implements WebserviceListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private UserInfoParser userInfo;
    private View rootView;

    public PlaceholderFragment() {

    }

    public void initData(UserInfoParser userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_user_details, container, false);
        populate();
        return rootView;
    }

    private void populate() {
        TextView fullname = (TextView) rootView.findViewById(R.id.user_data_fullname);
        TextView address = rootView.findViewById(R.id.user_data_address);
        TextView phone = rootView.findViewById(R.id.user_data_phone);
        TextView birthdate = rootView.findViewById(R.id.user_data_birthdate);
        TextView age = rootView.findViewById(R.id.user_data_age);
        TextView email = rootView.findViewById(R.id.user_data_email);

        String fullnameTxt = userInfo.getFirstName() + " " + userInfo.getLastName();
        String phoneTxt = userInfo.getPhone();
        String birthDateTxt = userInfo.getParsedBirthDate();
        String birthDateBrutTxt = userInfo.getBirthDate();
        String emailTxt = userInfo.getEmail();

        String addressTxt = "";
        if (userInfo.getAddress() != null) {
            addressTxt = userInfo.getAddress().getShortAdress();
        }

        Calendar today = Calendar.getInstance();
        Calendar birthDateCal = Calendar.getInstance();

        int ageTxt = 0;
        try {
            SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date convertedDate = StringToDate.parse(birthDateBrutTxt);
            birthDateCal.setTime(convertedDate);
            ageTxt = today.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fullname.setText(fullnameTxt);
        address.setText(addressTxt);
        phone.setText(phoneTxt);
        birthdate.setText(birthDateTxt);
        age.setText(String.valueOf(ageTxt));
        email.setText(emailTxt);

        new GetClubs().execute();
    }

    private void updateClubList(ArrayList<Object> datas) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_club);
        if (recyclerView != null && datas != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            ClubListAdapter clubListAdapter = new ClubListAdapter((ViewUserDetailsActivity) getActivity(), datas);
            recyclerView.setAdapter(clubListAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        }

        rootView.findViewById(R.id.progress_club).setVisibility(View.GONE);
    }

    @Override
    public void onWebserviceFinishWithSuccess(String method, final String id, final ArrayList<Object> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((datas != null) && id != null && id.equals(String.valueOf(userInfo.getId())) && (datas.get(0) instanceof ClubParser)) {
                    updateClubList(datas);
                }
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error) {
        updateClubList(null);
    }

    private class GetClubs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ClubsDao profileDao = new ClubsDao();
                profileDao.getClubs(PlaceholderFragment.this, String.valueOf(userInfo.getId()), PreferencesUtils.getToken(getActivity().getApplicationContext()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}