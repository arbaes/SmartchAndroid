package eu.creapix.louisss13.smartchandoid.conroller.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.dataAccess.ProfileDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.AccountParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.UserInfoParser;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
import eu.creapix.louisss13.smartchandoid.utils.Utils;


//TODO - Affichage des différents UserInfos ( classer par club ? )
public class ProfileActivity extends BaseActivity implements View.OnClickListener, WebserviceListener {

    private EditText firstName, lastName, email, password, confirmPassword;
    private TextView headerFullName, headerEmail;
    private FloatingActionButton edit;
    private Button mGetUserInfos;
    private boolean inEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        headerFullName = (TextView) findViewById(R.id.header_full_name);
        headerEmail = (TextView) findViewById(R.id.header_email);
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_pwd);
        edit = (FloatingActionButton) findViewById(R.id.edit);

        edit.setOnClickListener(this);

        mGetUserInfos = findViewById(R.id.get_userinfo);
        mGetUserInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.hasConnexion(getApplicationContext())) {
                    Intent intent = new Intent(ProfileActivity.this, ViewUserDetailsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ProfileActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                    findViewById(R.id.progress).setVisibility(View.GONE);
                }
            }
        });
        if (Utils.hasConnexion(getApplicationContext())) {
            new GetDatas().execute();
        } else {
            Toast.makeText(ProfileActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            findViewById(R.id.progress).setVisibility(View.GONE);
        }


    }

    private void populateDatas(String firstNameTxt, String lastNameTxt, String emailTxt) {

        String fullNameTxt = firstNameTxt + " " + lastNameTxt;
        if (!StringUtils.isEmpty(firstNameTxt)) {
            firstName.setText(firstNameTxt);
            PreferencesUtils.saveFirstName(getApplicationContext(), firstName.getText().toString());
        }
        if (!StringUtils.isEmpty(lastNameTxt)) {
            lastName.setText(lastNameTxt);
            PreferencesUtils.saveLastName(getApplicationContext(), lastName.getText().toString());
        }

        if (!StringUtils.isEmpty(fullNameTxt))
            headerFullName.setText(fullNameTxt);

        if (!StringUtils.isEmpty(emailTxt)) {
            headerEmail.setText(emailTxt);
            email.setText(emailTxt);
            PreferencesUtils.saveEmail(getApplicationContext(), headerEmail.getText().toString());
        }
    }

    private void handleEdit(boolean show) {
        inEdit = show;
        findViewById(R.id.change_password).setVisibility(inEdit ? View.VISIBLE : View.GONE);
        findViewById(R.id.first_name).setEnabled(inEdit);
        findViewById(R.id.last_name).setEnabled(inEdit);
        //findViewById(R.id.email).setEnabled(inEdit);
        edit.setImageResource(show ? R.drawable.ic_done : R.drawable.ic_edit_black_24dp);
        mGetUserInfos.setVisibility(show ? View.GONE : View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(show ? R.drawable.ic_clear : R.drawable.ic_menu);

        ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(show ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inEdit) handleEdit(false);
                else handleOnClick();
            }
        });
    }

    private void handleSave() {
        // TODO call webservice + listener and put this code after in the response of the webservice

        PreferencesUtils.saveFirstName(getApplicationContext(), firstName.getText().toString());
        PreferencesUtils.saveLastName(getApplicationContext(), lastName.getText().toString());
        handleEdit(false);
        // TODO when new datas are available re call populateDatas to update wiew
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit:
                if (!inEdit) handleEdit(true);
                else handleSave();
                break;
        }
    }

    private class GetDatas extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ProfileDao profileDao = new ProfileDao();
                profileDao.getProfile(ProfileActivity.this, PreferencesUtils.getToken(getApplicationContext()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public void onWebserviceFinishWithSuccess(String method, Integer id, final ArrayList<Object> datas) {
        Log.e("SUCCESS WebServ", "" + datas.get(0).getClass());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (datas.get(0) instanceof AccountParser) {
                    AccountParser account = (AccountParser) datas.get(0);
                    UserInfoParser[] userInfo = account.getUserInfos();
                    if (userInfo.length > 0) {
                        populateDatas(userInfo[0].getFirstName(), userInfo[0].getLastName(), account.getEmail());
                    } else {
                        Toast.makeText(ProfileActivity.this, R.string.no_data, Toast.LENGTH_SHORT).show();
                        findViewById(R.id.progress).setVisibility(View.GONE);

                    }

                    findViewById(R.id.details).setVisibility(View.VISIBLE);
                    findViewById(R.id.progress).setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onWebserviceFinishWithError(String error, final int errorCode) {
        Log.e("ERROR WebServ", "" + error);
        findViewById(R.id.details).setVisibility(View.VISIBLE);
        findViewById(R.id.progress).setVisibility(View.GONE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (errorCode) {
                    case 400:
                        Toast.makeText(ProfileActivity.this, R.string.error_unauthorized, Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Utils.alertSessionExpired(ProfileActivity.this);
                        break;
                    default:
                        Utils.alertError(ProfileActivity.this,getString(R.string.server_error_title), getString(R.string.server_error_content));
                        break;
                }
            }
        });
    }
}
