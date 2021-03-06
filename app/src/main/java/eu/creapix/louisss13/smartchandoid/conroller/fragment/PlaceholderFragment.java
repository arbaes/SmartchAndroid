package eu.creapix.louisss13.smartchandoid.conroller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.conroller.activities.ViewUserDetailsActivity;
import eu.creapix.louisss13.smartchandoid.conroller.adapter.ClubListAdapter;
import eu.creapix.louisss13.smartchandoid.dataAccess.ClubsDao;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ClubParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.UserInfoParser;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

/**
 * Created by Arnaud Baes on 05-01-18.
 * IG-3C 2017 - 2018
 */

public class PlaceholderFragment extends Fragment implements WebserviceListener {

    private UserInfoParser userInfo;
    private View rootView;
    private TextView noDataView;


    public PlaceholderFragment() {
    }

    public void initData(UserInfoParser userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_user_details, container, false);
        populate();
        noDataView = rootView.findViewById(R.id.no_data_view);
        noDataView.setVisibility(View.GONE);
        return rootView;
    }

    private void populate() {
        TextView fullname = rootView.findViewById(R.id.user_data_fullname);
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
            SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",new Locale(Locale.getDefault().getLanguage()));
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


        if (Utils.hasConnexion(super.getContext())) {
            new GetClubs().execute();
        } else {
            Toast.makeText(getActivity(), R.string.no_connection, Toast.LENGTH_SHORT).show();
        }

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
    public void onWebserviceFinishWithSuccess(String method, final Integer id, final ArrayList<Object> datas) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ((datas != null) && (id != null) && (datas.size() > 0) && id.equals(userInfo.getId()) && (datas.get(0) instanceof ClubParser)) {
                    updateClubList(datas);
                } else {
                    noDataView = rootView.findViewById(R.id.no_data_view);
                    noDataView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error, int errorCode) {
        updateClubList(null);
    }

    private class GetClubs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ClubsDao profileDao = new ClubsDao();
                profileDao.getClubs(PlaceholderFragment.this, userInfo.getId(), PreferencesUtils.getToken(getActivity().getApplicationContext()));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}