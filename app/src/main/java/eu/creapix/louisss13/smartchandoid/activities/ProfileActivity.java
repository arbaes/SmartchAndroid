package eu.creapix.louisss13.smartchandoid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private EditText firstName, lastName, email, password, confirmPassword;
    private TextView headerFullName, headerEmail;
    private FloatingActionButton edit;
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

        populateDatas();
    }

    private void populateDatas() {
        String firstNameTxt = PreferencesUtils.getFirstName(getApplicationContext());
        String lastNameTxt = PreferencesUtils.getLastname(getApplicationContext());
        String fullNameTxt = PreferencesUtils.getFirstName(getApplicationContext()) + " " + PreferencesUtils.getLastname(getApplicationContext());

        if (!StringUtils.isEmpty(firstNameTxt))
            firstName.setText(firstNameTxt);

        if (!StringUtils.isEmpty(lastNameTxt))
            lastName.setText(lastNameTxt);

        if (!StringUtils.isEmpty(fullNameTxt))
            headerFullName.setText(fullNameTxt);

        String emailTxt = PreferencesUtils.getEmail(getApplicationContext());
        if (!StringUtils.isEmpty(emailTxt)) {
            headerEmail.setText(emailTxt);
            email.setText(emailTxt);
        }
    }

    private void handleEdit(boolean show) {
        inEdit = show;
        findViewById(R.id.change_password).setVisibility(inEdit ? View.VISIBLE : View.GONE);
        findViewById(R.id.first_name).setEnabled(inEdit);
        findViewById(R.id.last_name).setEnabled(inEdit);
        //findViewById(R.id.email).setEnabled(inEdit);
        edit.setImageResource(show ? R.drawable.ic_done : R.drawable.ic_edit_black_24dp);

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
}
