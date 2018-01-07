package eu.creapix.louisss13.smartchandoid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.UsersDao;
import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.Utils;


public class RegisterActivity extends AppCompatActivity implements WebserviceListener {

    private RegisterTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    if (Utils.hasConnexion(getApplicationContext())) {
                        attemptRegister();
                    } else {
                        Toast.makeText(RegisterActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.hasConnexion(getApplicationContext())) {
                    attemptRegister();
                } else {
                    Toast.makeText(RegisterActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password_too_short_part1));
            focusView = mPasswordView;
            cancel = true;
        } else {
            boolean[] pwdValidity = Utils.PasswordValidity(password);
            if (!(pwdValidity[Constants.HAS_UPPER_CASE])){
                mPasswordView.setError(getString(R.string.error_invalid_password_no_uppercase));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_LOWER_CASE])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_no_lowercase));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_DIGIT])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_no_digit));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_SPECIAL_CHAR])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_no_special));
                focusView = mPasswordView;
                cancel = true;
            } else if (!(pwdValidity[Constants.HAS_ENOUGH_UNIQUE_CHAR])) {
                mPasswordView.setError(getString(R.string.error_invalid_password_not_enough_unique_part1) +
                        " " + String.valueOf(Constants.MIN_UNIQUE_CHAR_REQUIRED + " " +
                        getString(R.string.error_invalid_password_not_enough_unique_part2)));
                focusView = mPasswordView;
                cancel = true;
            }
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            mAuthTask = new RegisterActivity.RegisterTask(email, password);
            if (Utils.hasConnexion(getApplicationContext())) {
                mAuthTask.execute((Void) null);
            } else {
                Toast.makeText(RegisterActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();

            }

        }
    }

    private boolean isEmailValid(String email) {

        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);
    }

    private boolean isPasswordValid(String password) {

        return password.length() > Constants.MIN_CHAR_REQUIRED;
    }

    @Override
    public void onWebserviceFinishWithSuccess(String method, Integer id, ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                loginIntent.putExtra(Constants.LOGIN, mEmailView.getText().toString());
                loginIntent.putExtra(Constants.PWD, mPasswordView.getText().toString());
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(loginIntent);
                finish();
            }
        });

    }

    @Override
    public void onWebserviceFinishWithError(String error, final int errorCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView errorField = findViewById(R.id.account_incorrect);

                switch (errorCode) {
                    case 400:
                        mEmailView.setError(getString(R.string.registered_already));
                        mEmailView.requestFocus();
                        break;
                    default:
                        errorField.setVisibility(View.VISIBLE);
                        errorField.setText(R.string.server_error_content);
                }
            }
        });
    }


    private class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private UsersDao userDao;

        RegisterTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            userDao = new UsersDao();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                return userDao.register(RegisterActivity.this, mEmail,mPassword);
            }
            catch (IOException e) {
                e.printStackTrace();
            }



            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, R.string.something_wrong, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

